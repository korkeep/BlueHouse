# What-Citizens-Want
## Project Goal
Keyword Analysis & Visualization of the Blue-House National Petition Data

## Expected Result
### 1. Analysis of public opinion
- TF-IDF keyword analysis → elicit public opinion
- Classification anaylsis results by number of participants, category, monthly
### 2. How they changed our lives
- How public opinion has affected the trial → comparison with similar trials in the past
- Whether the legislation's imprisonment weight was set correctly or not → Minsik-Law, YoonChangho-Law ···
- Difference between government's stance and public opinion → DPRK, real-estate, prosecution, COVID-19 ···
### 3. Good/Poor part analysis
- Suggestions for improvement → Target: Government, Congress, Court, Media

## To-Do List
- **Acquisition**: Data Crawling & Pre-Processing (Python → Selenium, TF-IDF)
- **Analysis**: Process Data in API Format (Python → Spark SQL, NLP)
- **Visualization**: Android APP Development (Kotlin → Android Studio)

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