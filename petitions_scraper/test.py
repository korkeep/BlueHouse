import re
import requests
from bs4 import BeautifulSoup
from time import gmtime, strftime


def get_soup(url,header):
    r = requests.get(url, header, timeout=3)
    html = r.text
    page = BeautifulSoup(html, 'lxml')
    return page

url='https://www1.president.go.kr/petitions/584550'
page=get_soup(url,"https://www1.president.go.kr/petitions/?c=0&only=2&page=4&order=1")
print(page)