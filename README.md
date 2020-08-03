
## What-Citizens-Want
### Project Goal
Keyword Analysis & Visualization of the Blue-House National Petition Data
1. 국민 여론은 어떠한가?
- tf-idf키워드 분석 → 국민 여론 도출
- 월별, 카테고리별, 참여인원별 분류

2. 우리 삶을 얼마나 바꿨나?
- 정부·국회·법원·언론
Ex) 국민 여론이 사안에 어떤 영향을 주었는지 → 과거 유사한 재판과 비교
Ex) 입법된 법안의 형량 경중이 올바르게 책정됐는지 → 민식이법, 윤창호법 비교
Ex) 정부의 스탠스와 언론, 여론은 어떤 차이가 있는지 → 북한, 부동산법, 최저임금, 검찰개혁, 코로나

3. 잘한/미흡한 부분은 무엇인가?
- 정부·국회·법원·언론의 개선 방향 제시

### To-Do List
- Acquisition: Data Crawling & Pre-Processing (Python → Selenium, TF-IDF)
- Analysis: Process Data in API Format (Python → Spark SQL, NLP)
- Visualization: Android APP Development (Kotlin → Android Studio)

### API Foramt
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