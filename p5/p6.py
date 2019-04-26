import pandas
import collections
import numpy as np
import matplotlib.pyplot as plt
df = pandas.read_csv('cardata.csv')
print('mean value for Retail($): ')
print(df['Retail($)'].mean())
print('mean value for Horsepower: ')
print(df['Horsepower'].mean())
index = 1
for entry in list(df):
    index += 1
    if index < 4:
        continue
    std = df[entry].std()
    mean = df[entry].mean()
    df = df.astype({entry: np.float})
    for row in df.index.tolist():
        df.at[row, entry] = (df.at[row, entry] - mean)/std
array = df.values
array = np.delete(array, 0, axis=1)
array = np.delete(array, 0, axis=1)
covariance = np.cov(array.T.astype(float))
eig_val, eig_vector = np.linalg.eigh(covariance)
eig_temp = {}
for a, b in zip(eig_val, eig_vector):
    eig_temp[a] = b
eigs = collections.OrderedDict(sorted(eig_temp.items(), reverse=True))
print('first eigenvector: ')
eig1 = list(eigs.items())[0][1]
print(eig1)
print('third eigenvector: ')
eig2 = list(eigs.items())[1][1]
eig3 = list(eigs.items())[2][1]
print(eig3)
df1 = pandas.read_csv('cardata.csv')
for row in df1.index.tolist():
    if df1.at[row, 'Category'] != 'minivan':
        df1 = df1.drop(row)
array = df1.values
array = np.delete(array, 0, axis=1)
array = np.delete(array, 0, axis=1)
v1 = np.dot(array, eig1)
v2 = np.dot(array, eig2)
plt.plot(v1, v2, 'r.', label='minivan')
df1 = pandas.read_csv('cardata.csv')
for row in df1.index.tolist():
    if df1.at[row, 'Category'] != 'sedan':
        df1 = df1.drop(row)
array = df1.values
array = np.delete(array, 0, axis=1)
array = np.delete(array, 0, axis=1)
v1 = np.dot(array, eig1)
v2 = np.dot(array, eig2)
plt.plot(v1, v2, 'b.', label='sedan')
df1 = pandas.read_csv('cardata.csv')
for row in df1.index.tolist():
    if df1.at[row, 'Category'] != 'suv':
        df1 = df1.drop(row)
array = df1.values
array = np.delete(array, 0, axis=1)
array = np.delete(array, 0, axis=1)
v1 = np.dot(array, eig1)
v2 = np.dot(array, eig2)
plt.plot(v1, v2, 'g.', label='suv')
plt.legend(loc='upper left')
plt.xlabel('v1')
plt.ylabel('v2')
plt.show()
