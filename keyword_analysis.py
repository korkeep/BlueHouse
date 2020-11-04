import json
import os
from math import log10
from konlpy.tag import Okt
from konlpy.corpus import kobill
# =======================================
# -- TF-IDF Function
# =======================================
def f(t, d):
    # d is document == tokens
    return d.count(t)

def tf(t, d):
    # d is document == tokens
    return 0.5 + 0.5*f(t,d)/max([f(w,d) for w in d])

def idf(t, D):
    # D is documents == document list
    numerator = len(D)
    denominator = 1 + len([ True for d in D if t in d])
    return log10(numerator/denominator)

def tfidf(t, d, D):
    return tf(t,d)*idf(t, D)

def tokenizer(d):
    okt = Okt()
    def keyword_extractor(text):
        tokens = okt.nouns(text) # KoNLPy → 명사 토크나이징
        tokens = set(tokens) # set 변환 → 중복 제거
        tokens = list(tokens) # list 변환 → 중복 제거
        tokens = [token for token in tokens if len(token) > 1]  # 한 글자인 단어는 제외
        count_dict = [(token, text.count(token)) for token in tokens]
        ranked_words = sorted(count_dict, key=lambda x: x[1], reverse=True)[:20]
        return [keyword for keyword, freq in ranked_words]
    return keyword_extractor(d)

def tfidfScorer(D):
    tokenized_D = [tokenizer(d) for d in D]
    result = []
    for d in tokenized_D:
        result.append([(t, tfidf(t, d, tokenized_D)) for t in d])
    return result

# =======================================
# -- Keyword Analysis
# =======================================

R_filepath = 'output/'
W_filepath = 'API/'

json_files = [pos_json for pos_json in os.listdir(R_filepath) if pos_json.endswith('.json')]

for index, js in enumerate(json_files):
    with open(os.path.join(R_filepath, js), 'rt', encoding='UTF8') as json_file:
        raw_data = json.load(json_file)
        raw_content = raw_data["content"]

        keyword = []
        corpus = [raw_content]
        print(corpus)
        
        # Extract 10 keywords
        for id, s in enumerate(tfidfScorer(corpus)):
            s = sorted(s, key=lambda x:x[1], reverse=True)
            for i in range(len(s)):
                keyword.append(s[i][0])
                if len(keyword) == 10: break

        print(keyword)
        raw_data["keyword"] = keyword

        # Make API json files
        if not os.path.isdir(W_filepath):
            os.mkdir(W_filepath)
        with open(os.path.join(W_filepath, js), 'w', encoding='utf-8') as fd:
            json.dump(raw_data, fd, ensure_ascii=False, indent=2)
