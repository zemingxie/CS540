import collections
import os
from operator import itemgetter
import matplotlib.pyplot as plt
import math
import numpy as np
cnt = collections.Counter()
entries = os.listdir('news/')
wordcount = 0
word_list = []
for entry in entries:
    with open('news/' + entry, 'r') as f:
        raw_data = f.read()
    data = raw_data.split()
    wordcount += len(data)
    cnt += collections.Counter(data)
print('word tokens: ')
print(wordcount)
print('word types: ')
print(len(cnt))
print('top 20 word types and count: ')
for entry in cnt.most_common(20):
    print(entry)
for entry in cnt.items():
    word_list.append(entry[1])
word_list.sort(reverse=True)
count = np.array(word_list)
rank = np.array(list(range(1, len(cnt)+1)))
print('image 3a and 3b is saved in hw6_3a.png and hw6_3b.png')
print('run one plot at a time, comment out the other plot when plotting one')
#plot 3a
plt.plot(rank, count)
plt.xlabel('rank')
plt.ylabel('count')
plt.savefig('hw6_3a')
#plot 3b
#count = np.log1p(count)
#rank = np.log1p(rank)
#plt.plot(rank, count)
#plt.xlabel('log rank')
#plt.ylabel('log count')
#plt.savefig('hw6_3b')
with open('news/098.txt', 'r') as f:
    raw_data = f.read()
data = raw_data.split()
tf_bot = collections.Counter(data).most_common(1)[0][1]
tf_top = collections.Counter(data)['contract']
tf = tf_top/tf_bot
idf_top = len(entries)
idf_bot = 0
for entry in entries:
    with open('news/' + entry, 'r') as f:
        raw_data = f.read()
    data = raw_data.split()
    if collections.Counter(data)['contract'] != 0:
        idf_bot += 1
idf = math.log10(idf_top/idf_bot)
tf_idf = tf * idf
print('tf-idf of contract')
print(tf_idf)
with open('news/098.txt', 'r') as f:
    raw_data = f.read()
data = raw_data.split()
top_10_tf_idf = {}
for index in collections.Counter(data):
    tf_top = collections.Counter(data)[index]
    tf = tf_top / tf_bot
    idf_bot = 0
    for entry in entries:
        with open('news/' + entry, 'r') as f:
            raw_data = f.read()
        new_data = raw_data.split()
        if collections.Counter(new_data)[index] != 0:
            idf_bot += 1
    idf = math.log10(idf_top / idf_bot)
    tf_idf = tf * idf
    top_10_tf_idf[index] = tf_idf
new_list_tf_idf = sorted(top_10_tf_idf.items(), key=itemgetter(1), reverse=True)
print('top 10 tf-idf:')
counter = 0
for entry in new_list_tf_idf:
    print(entry)
    counter += 1
    if counter > 9:
        break
with open('news/098.txt', 'r') as f:
    raw_data = f.read()
data1 = raw_data.split()
with open('news/297.txt', 'r') as f:
    raw_data = f.read()
data2 = raw_data.split()
cnt = collections.Counter(data1) + collections.Counter(data2)
z_d = len(data1)
vector_x_temp = []
for entry in cnt:
    if entry in dict(collections.Counter(data1)):
        vector_x_temp.append(dict(collections.Counter(data1))[entry])
    else:
        vector_x_temp.append(0)
vector_x = np.array(vector_x_temp)
vector_x = vector_x/z_d
z_d = len(data2)
vector_y_temp = []
for entry in cnt:
    if entry in dict(collections.Counter(data2)):
        vector_y_temp.append(dict(collections.Counter(data2))[entry])
    else:
        vector_y_temp.append(0)
vector_y = np.array(vector_y_temp)
vector_y = vector_y/z_d
sim_top = np.dot(vector_x.T, vector_y)
sim_bot_left = math.sqrt(np.dot(vector_x.T, vector_x))
sim_bot_right = math.sqrt(np.dot(vector_y.T, vector_y))
sim = sim_top/sim_bot_left/sim_bot_right
print('bag-of-words cosine similarity: ')
print(sim)
top_10_tf_idf1 = {}
with open('news/297.txt', 'r') as f:
    raw_data = f.read()
data = raw_data.split()
for index in collections.Counter(data):
    tf_top = collections.Counter(data)[index]
    tf = tf_top / tf_bot
    idf_bot = 0
    for entry in entries:
        with open('news/' + entry, 'r') as f:
            raw_data = f.read()
        new_data = raw_data.split()
        if collections.Counter(new_data)[index] != 0:
            idf_bot += 1
    idf = math.log10(idf_top / idf_bot)
    tf_idf = tf * idf
    top_10_tf_idf1[index] = tf_idf
vector_x_temp = []
for entry in cnt:
    if entry in top_10_tf_idf:
        vector_x_temp.append(top_10_tf_idf[entry])
    else:
        vector_x_temp.append(0)
vector_x = np.array(vector_x_temp)
vector_y_temp = []
for entry in cnt:
    if entry in top_10_tf_idf1:
        vector_y_temp.append(top_10_tf_idf1[entry])
    else:
        vector_y_temp.append(0)
vector_y = np.array(vector_y_temp)
sim_top = np.dot(vector_x.T, vector_y)
sim_bot_left = math.sqrt(np.dot(vector_x.T, vector_x))
sim_bot_right = math.sqrt(np.dot(vector_y.T, vector_y))
sim = sim_top/sim_bot_left/sim_bot_right
print('tf-idf cosine similarity: ')
print(sim)
