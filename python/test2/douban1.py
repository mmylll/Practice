# -*- coding:UTF-8 -*-
import requests
import json

class DoubanSpider():
    def __init__(self):
        self.url = {
            "url_temp": "https://m.douban.com/rexxar/api/v2/subject_collection/tv_domestic/items?&start={}&count=18&loc_id=108288",

            "country": "us"}
        self.headers = {"Referer": "https://m.douban.com/tv/chinese",

                        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"

                        }

    def parse_url(self, url):  # 发送请求，获取响应
        print(url)
        response = requests.get(url, headers=self.headers)
        return response.content.decode()

    def get_content_list(self, json_str):  # 提取数据

        dict_ret = json.loads(json_str)  # 转化为python字典对象
        content_list = dict_ret["subject_collection_items"]  # 通过字典的键得到相应的值，从而获取页面数据列表

        for a in content_list:
            print(a['title'])
        return content_list

    def save_content_list(self, content_list, country):  # 保存数据
        for content in content_list:
            with open('douban_my.txt', 'a', encoding='UTF-8') as f:
                content["country"] = country
                f.write(json.dumps(content, ensure_ascii=False))
                f.write('\n')
        print('保存成功')

    def run(self):  # 实现主要逻辑
        num = 0
        while True:
            # 1、获取第一页url
            start_url = self.url["url_temp"].format(num)
            # 2、发送请求，获取响应
            json_str = self.parse_url(start_url)
            # 3、提取数据
            content_list = self.get_content_list(json_str)
            # # 4、保存
            #self.save_content_list(content_list, self.url["country"])
            # 5、获取第二页url
            if len(content_list) < 18:  # 判断是否到了最后一页
                break
            num += 18


if __name__ == '__main__':
    doubanspider = DoubanSpider()
    doubanspider.run()