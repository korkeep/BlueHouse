# BlueHouse
## Project Goal
Keyword Analysis & Visualization of the Blue-House National Petition Data <img title="South Korea" alt="South Korea" src="https://image.flaticon.com/icons/svg/197/197582.svg" width="15"/>

## Development Process
- **Acquisition**: Data Crawling (Python → Beautifulsoup)
- **Pre-Processing**: Data Tokenizing (Python → TF-IDF)
- **Analysis**: Process Data in API Format (Python → KoNLP)
- **Visualization**: Android APP Development (Java → Android)

## API Format
| Content | Description |
| --- | --- |
| time | 수집시각 |
| begin | 시작일 |
| end | 종료일 |
| category | 카테고리 (정치개혁, 일자리 등) |
| title | 제목 |
| content | 내용 |
| keyword | 키워드 |
| agree | 참여인원 |
| status | 진행 상황 (청원시작, 청원진행중, 청원종료, 답변완료) |
```python
from petitions_scraper import parse_page

url = 'https://www1.president.go.kr/petitions/<number>'
parse_page(url)
```

```
<Example>
{'time': '2020-08-01 15:00:00',
 'begin': '2020-07-10',
 'end': '2020-08-09',
 'category': '인권/성평등',
 'title': '박원순씨 장례를 5일장, 서울특별시장(葬)으로 하는 것 반대합니다.',
 'content': '박원순씨가 사망하는 바람에 성추행 의혹은 수사도 하지 못한 채 ··· <후략>',
 'keyword': '박원순, 성추행, 자살 ··· <후략>',
 'agree': 593596,
 'status': '청원진행중'}
 ```

## Application Design
### 1. Acquisition
![acq](https://user-images.githubusercontent.com/20378368/105618538-c401f780-5e2b-11eb-9483-fa3e5f7f3a7e.PNG)
- 데이터 수집(Crawling) 단계
- BeautifulSoup: HTML 데이터 크롤링 라이브러리  
### 2. Pre-Processing
![pre](https://user-images.githubusercontent.com/20378368/105618539-c5332480-5e2b-11eb-908c-4cf38ebebdc2.PNG)
- 자연어 처리(NLP; Natural Language Processing) 단계
- KoNLPy: 한글 형태소 분석 라이브러리  
### 3. Analysis
![ana](https://user-images.githubusercontent.com/20378368/105618540-c5cbbb00-5e2b-11eb-9166-74d0271164be.PNG)
- 키워드 분석 단계
- TF-IDF: 키워드 추출 알고리즘  
### 4. Visualization
![vis](https://user-images.githubusercontent.com/20378368/105618541-c5cbbb00-5e2b-11eb-920f-ddb15e5f21bb.PNG)
- 분석 결과 → 시각화 단계
- Android Application 개발  

## Result
### Analysis of public opinion
- TF-IDF keyword analysis → elicit public opinion
- Classification → number of participants, category, month
### How they changed our lives
- How public opinion has affected the trial → Me-Too, 'N'th room, Appointing Cho-Kuk as minister of justice ···
- Whether the legislation's imprisonment weight was set correctly or not → Minsik law, Yoon-Changho law ···
- Difference between government's stance and public opinion → DPRK, Real estate, Prosecution, COVID-19 ···
### Good/Poor part classification
- Suggestions for improvement → Target: Government, Congress, Court, Media

## Demo Video
**[YouTube Link](https://www.youtube.com/watch?v=lhAaXeZExQY)**  
![image](https://user-images.githubusercontent.com/20378368/108582100-643c3500-7374-11eb-8ee0-80f659644c8d.png)
