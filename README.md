
# What-Citizens-Want
## Project Goal
Keyword Analysis & Visualization of the Blue-House National Petition Data

## The Blue-House national petition
### 1. Anaylisis of public opinion
- TF-IDF keyword analysis → elicit public opinion
- By number of participants, category, monthly classification
### 2. How they changed our lives
- Government·Congress·Court·Media
- Ex) How public opinion has affected the trial → Comparison with similar trials in the past
- Ex) The legislation's imprisonment weight was set correctly or not → Minsik-Law, YoonChangho-Law comparison
- Ex) Difference between the government's stance and public opinion → North-Korea, Real estate Law, Minimum wage, Prosecution reform, COVID-19
### 3. Good/Poor partial analysis
- Suggestions for improving government·congress·court·media

## To-Do List
- Acquisition: Data Crawling & Pre-Processing (Python → Selenium, TF-IDF)
- Analysis: Process Data in API Format (Python → Spark SQL, NLP)
- Visualization: Android APP Development (Kotlin → Android Studio)

## API Foramt
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