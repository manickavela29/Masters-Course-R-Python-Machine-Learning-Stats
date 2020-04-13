import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import mylib

data = pd.read_csv('mentrackrecords.csv')

meanv = mylib.mean(data)
cov = np.matrix(data.cov().values)

covI = cov.I


mylib.normalitycheck(data,covI,meanv)
cor , eigen = mylib.corrAndEigen(data)

pca = mylib.PCA(data,cov,eigen)
pcadata , pcacor = mylib.pcacorrelation(data,meanv,cov,cor,pca,eigen)

mylib.rank(pca,pcadata,data)

mylib.graphicalanalysis(pca,pcadata)

mylib.regression(data)

mylib.clusters(data)

mylib.regression(data)