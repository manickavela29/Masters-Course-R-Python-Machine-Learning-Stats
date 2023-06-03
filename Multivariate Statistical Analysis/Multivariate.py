import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

import mylib

data = pd.read_csv('radiation.csv',index_col = None)
npdata = data.values

mean = mylib.mean(npdata)
print('--------- Mean -----------')
print('Mean         : ',mean)
print('Sanity check : ',(data['x1'].values).mean(),(data['x2'].values).mean())
print('\n\n')

covariance = mylib.covariance(npdata)
print('--------- Covariance -----------')
print('Covariance   : ',covariance)
print('Sanity check : \n',data.cov())
print('\n\n')

correlation = mylib.correlation(npdata)
print('--------- Correlation -----------')
print('Correlation  : ',correlation)
print('Sanity check : \n',data.corr())
print('\n\n')

eigenvalues = mylib.eigenvalues(npdata)
print('--------- Eigen  -----------')
print('Eigenvalues   : ',eigenvalues)

eigenvectors = mylib.eigenvectors(npdata)
print('Eigen vectors : ',eigenvectors)
print('\nSanity check  : \n',np.linalg.eig(covariance))

print('\n\n')
print('--------- Length of axis -----------')
length = mylib.lengthofaxis(npdata,0.05)
print('With significence 0.05 : ',length)

length = mylib.lengthofaxis(npdata,0.1)
print('With significence 0.1  : ',length)
print('\n\n')

print('--------- Euclidian Distance -----------')
l = mylib.euclidiandistance(npdata)
print('Euclidian distance   : ',l)
print('\n\n')

print('--------- Mahalonabis Distance -----------')
m = mylib.mahalonabisdistance(npdata)
print('Mahalonobis distance : ',m)
print('\n\n')

mylib.scatterdiagram(data)

print('--------- Comparing distances ------')
p = mylib.comparingEdMd(npdata)
print('Paired t-test t\'s value : ',p)
print('\n\n')

mylib.contours(data)

mylib.QQ_plot(data)

mylib.chi_square_plot(data)

print('-------  Intervals  ----------')
intervals = mylib.intervals(data)
print('Intervals : ',intervals)
print('\n\n')

print('--------- Comparing Intervals ------------')
mylib.comparing_intervals(data)
print('\n')