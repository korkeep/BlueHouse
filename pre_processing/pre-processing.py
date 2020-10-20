import csv
import json
import pathlib
from time import time


def concat_json(is_best=True, hangul_only=True, use_double_space=True):
    t = time()
    json_path = '../acquisition/json_of_best/' if is_best else '../acquisition/json_of_all/'
    save_filename = f"{'best' if is_best else 'all'}_comments{'_hangul' if hangul_only else ''}"
    pwd = pathlib.Path.cwd()
    docs = []
    titles = []
    csv_f = None
    writer = None
    if not use_double_space:
        csv_f = open(f"{save_filename}.csv", 'w', encoding='utf-8', newline='')
        writer = csv.writer(csv_f)
        writer.writerow(['title_id', 'episode_num', 'comment_num', 'sentence', 'registered_time'])  # header
    for json_f in pwd.glob(json_path + '*.json'):
        title_id = json_f.stem
        sentences = []
        with json_f.open(encoding='utf-8') as f:
            for episode_num, comments in enumerate(json.load(f)):
                for comment_num, comment in enumerate(comments):
                    lines = comment["contents"]
                    if hangul_only:
                        double_space_lines = "".join(('  ' if c == '\n' else c) for c in lines if
                                                     ((44032 <= ord(c) <= 55203) or c == ' ' or c == '\n'))
                    else:
                        double_space_lines = "".join(
                            ('  ' if c == '\n' else c) for c in lines if c.isalnum() or c == ' ' or c == '\n')
                    if use_double_space:
                        sentences.append(double_space_lines)
                        titles.append(title_id)
                    else:
                        day_token = comment["regTime"][:10]
                        time_token = comment["regTime"][11:-5]
                        writer.writerow([title_id, episode_num, comment_num,
                                         double_space_lines, day_token+' '+time_token])
            docs.append('  '.join(sentences))
    if csv_f is not None:
        csv_f.close()
    if use_double_space:
        with open(f"{save_filename}.corpus", 'w', encoding='utf-8') as txt:
            txt.write('\n'.join(docs))
        with open(f"{save_filename}.corpus.title_id.txt", 'w', encoding='utf-8') as title_txt:
            title_txt.write('\n'.join(titles))

    print(f"Done: {time() - t} seconds elapsed.")


if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description="Prepare comments data")
    parser.add_argument('-b', '--best', action='store_true', help='Use best comments rather than all of them.')
    parser.add_argument('-H', '--hangul_only', action='store_true', help='Filter non-hangul character.')
    parser.add_argument('-d', '--use_double_space', action='store_true',
                        help='Save as soynlp DoubleSpaceLineCorpus format. Otherwise, save as CSV.')
    args = parser.parse_args()
    concat_json(args.best, args.hangul_only, args.use_double_space)
