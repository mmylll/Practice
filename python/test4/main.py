# -*- coding:UTF-8 -*-
import requests
import json
import sys
import random
import string
import csv
import xlwt


class DoubanSpider():
    global li
    li = 0

    def __init__(self):
        self.url = [
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_american/items?start={}&count=18&loc_id=108288",
                "country": "us"},
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_domestic/items?&start={}&count=18&loc_id=108288",
                "country": "cn"},
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_korean/items?start={}&count=18&loc_id=108288",
                "country": "us"},
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_animation/items?start={}&count=18&loc_id=108288",
                "country": "us"},
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_variety_show/items?start={}&count=18&loc_id=108288",
                "country": "us"},
            {
                "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_japanese/items?start={}&count=18&loc_id=108288",
                "country": "jp"},
        ]
        self.headers = [
            {
                "Referer": "https://m.douban.com/tv/american",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"},
            {
                "Referer": "https://m.douban.com/tv/chinese",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
            },
            {
                "Referer": "https://m.douban.com/tv/korean",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"},
            {
                "Referer": "https://m.douban.com/tv/animation",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
            },
            {
                "Referer": "https://m.douban.com/tv/tvshow",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"},
            {
                "Referer": "https://m.douban.com/tv/japanese",
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
            }
        ]

    def parse_url(self, sort, url):  # ???????????????????????????
        print(url)
        response = requests.get(url, headers=self.headers[sort])
        return response.content.decode()

    def get_content_list(self, json_str):  # ????????????
        global li
        dict_ret = json.loads(json_str)  # ?????????python????????????
        content_list = dict_ret["subject_collection_items"]  # ?????????????????????????????????????????????????????????????????????
        for a in content_list:
            print(a['title'])
            li = li +1
            print(li)
        print(li)
        return content_list

    def save_content_list(self, content_list, country):  # ????????????
        for content in content_list:
            with open('douban_m.txt', 'a', encoding='UTF-8') as f:
                content["country"] = country
                f.write(str(content_list[3]['rating']['value'])+" "+str(content_list[3]['title'])+" "+str(content_list[3]['year'])+" "+str(content_list[3]['recommend_comment'])+" "+str(content_list[3]['recommend_comment'])+" "+str(content_list[3]['actors'][0])+" "+str(content_list[1]['directors'][0]))
                #f.write(json.dumps(content, ensure_ascii=False))
                f.write('\n')
        print('????????????')

    def run(self, sort):  # ??????????????????
        num = 0
        while True:
            # 1??????????????????url
            start_url = self.url[sort]["url_temp"].format(num)
            # 2??????????????????????????????
            json_str = self.parse_url(sort, start_url)
            # 3???????????????
            content_list = self.get_content_list(json_str)
            # # 4?????????
            self.save_content_list(content_list, self.url[1]["country"])
            # 5??????????????????url
            if len(content_list) < 18:  # ??????????????????????????????
                break
            num += 18
        return content_list


if __name__ == '__main__':
    doubanspider = DoubanSpider()
    doubanspider.run(2)
