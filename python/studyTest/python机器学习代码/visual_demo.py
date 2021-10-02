import seaborn as sns
import ssl
import numpy as np
import matplotlib.pyplot as plt
import random
import pandas as pd

ssl._create_default_https_context = ssl._create_unverified_context

plt.rcParams['font.sans-serif']=['SimHei'] #用来正常显示中文标签
plt.rcParams['axes.unicode_minus']=False #用来正常显示负号

np.random.seed(42)

df = pd.read_csv("st.csv", sep=',', header=None) # eval dataset

#小组得分直方图
np.random.seed(42)
number_of_bins = 30

# An example of three data sets to compare
number_of_data_points = 600
labels = ["组1",'组2','组3']
# data_sets = [np.random.normal(0, 1, number_of_data_points),
#              np.random.normal(6, 1, number_of_data_points),
#              np.random.normal(0, 4, number_of_data_points)]

data_sets = [list(df[df.columns[0]]),list(df[df.columns[1]]),list(df[df.columns[2]])]
print(data_sets)
# Computed quantities to aid plotting
hist_range = (np.min(data_sets), np.max(data_sets))
binned_data_sets = [
                    np.histogram(d, range=hist_range, bins=number_of_bins)[0]
                    for d in data_sets
                    ]
binned_maximums = np.max(binned_data_sets, axis=1)
x_locations = np.arange(0, sum(binned_maximums), np.max(binned_maximums))

# The bin_edges are the same for all of the histograms
bin_edges = np.linspace(hist_range[0], hist_range[1], number_of_bins + 1)
centers = 0.5 * (bin_edges + np.roll(bin_edges, 1))[:-1]
heights = np.diff(bin_edges)

# Cycle through and plot each histogram
fig, ax = plt.subplots()
for x_loc, binned_data in zip(x_locations, binned_data_sets):
    lefts = x_loc - 0.5 * binned_data
    ax.barh(centers, binned_data, height=heights, left=lefts)

ax.set_xticks(x_locations)
ax.set_xticklabels(labels)
ax.set_title('小组得分直方图')
ax.set_ylabel("得分")
plt.show()
#------------