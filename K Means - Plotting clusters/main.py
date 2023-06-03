from sklearn.cluster import KMeans
from sklearn import metrics
from scipy.spatial.distance import cdist
from scipy import stats
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import mylib

data = pd.read_csv('data.csv')
npdata = data.values
npdata = npdata[:,1:]

mylib.Optimumclusters(npdata)

clusters,centroids = mylib.Dividingtoclusters(npdata)

computes = []
for i in range(len(clusters)) :
    meanv,cov,eigvalue,eigvector = mylib.Computes(clusters[i],centroids[i])
    computes.append((meanv,cov,eigvalue,eigvector))
#computes will have computed data of all the clusters

mylib.ConfidenceEllipse(data,clusters,computes)
#print('Computes',computes)
mylib.Plotstodetermine(clusters,computes)

mylib.Normality_checking(data)
