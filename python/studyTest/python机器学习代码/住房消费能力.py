# -*- coding: utf-8 -*-
"""
Created on Fri Oct 18 23:14:41 2019

@author: admin
"""
import matplotlib.pyplot as plt
from sklearn.preprocessing import minmax_scale

wage = [10, 12, 14, 16, 18, 20, 24, 28, 31, 38]
price = [1.3, 2.4, 3.8, 4.1, 4.6, 5.2, 5.8, 6.4, 7.1, 8.1]
years = ['2010', '2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018', '2019']
wage = minmax_scale(wage)
price = minmax_scale(price)
plt.plot(years, wage, price)
plt.show()

buy_capacity = []
for i in range(0, 9):
    newv = (wage[i + 1] - wage[i]) / (price[i + 1] - price[i])
    buy_capacity.append(newv)

plt.plot(buy_capacity)
plt.title('个人住房消费能力')
plt.show()
