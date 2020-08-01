# What-Citizens-Want
Project Theme: Keyword Analysis of the Blue-House National Petition Data

## To-Do List
+ Acquisition: Raw Data Crawling
+ Analysis: Process Data in API format
+ Visualization: Android APP Development

## Data Foramt
| Content | Description |
| --- | --- |
| begin | 청원 시작일 |
| category | 청원 카테고리 (외교, 국방, 경제 등) |
| content | 청원 내용 |
| crawled_at | 수집 시각 |
| end | 청원 종료일 |
| num_agree | 수집 시각의 청원 동의 수 |
| replies | 청원 댓글 |
| status | 현재 청원 진행 상황 (청원시작, 청원진행중, 청원종료, 브리핑) |
```python
from petitions_scraper import parse_page

url = 'https://www1.president.go.kr/petitions/407329'
parse_page(url)
```

```
{'begin': '2018-10-15',
 'category': '경제민주화',
 'content': '금융위원회가 공모주 개인배정 축소(폐지)로 개인의 공모주 참여를 차단하려고 합니다 이와같은 금융위원회의 공모주 개인배정 축소(폐지)는 영세 개인 사업자의 골목상권을 빼앗아 가는 횡포와 같습니다. 이같은 행위는기업의 경제적 이익을 위해 개인의 밥그릇을 빼앗는 것으로 서민의 생계를 위협하는 처사입니다. 작금의 어려운 서민경제에서 가계에 조금이라도 보탬이 되고자 하는 개인 공모주 참여를 계속할 수 있도록 공모주 개인 배정 물량을 지금과 같이 할 수 있도록 꼭꼭 지켜주십시오.',
 'crawled_at': '2020-08-01 00:00:00',
 'end': '2018-11-14',
 'num_agree': 9,
 'status': '청원종료'}
 ```