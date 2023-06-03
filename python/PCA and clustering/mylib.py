import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from scipy import stats 
from sklearn.cluster import KMeans
from sklearn import metrics
from scipy.spatial.distance import cdist


def normalitycheck(data,covI,mean) :

    fig,axs = plt.subplots(3,3,figsize = (10,10))    

    npdata = data.values
    npdata = npdata[:,:-1]

    chi2test(axs[1,1],npdata,covI,mean)

    QQ_plot(axs[0,0],data[data.columns[0]].values,data.columns[0])
    QQ_plot(axs[0,1],data[data.columns[1]].values,data.columns[1])
    QQ_plot(axs[0,2],data[data.columns[2]].values,data.columns[2])
    QQ_plot(axs[1,0],data[data.columns[3]].values,data.columns[3])
    QQ_plot(axs[1,2],data[data.columns[4]].values,data.columns[4])
    QQ_plot(axs[2,0],data[data.columns[5]].values,data.columns[5])
    QQ_plot(axs[2,1],data[data.columns[6]].values,data.columns[6])
    stdq = QQ_plot(axs[2,2],data[data.columns[7]],data.columns[7])

   
    cov =np.zeros(8,dtype=float)
    stdq = np.array([stdq])
    for i in range(8) :
        d = np.array(data[data.columns[i]].values).reshape(1,55)
        m1 = 0 #stdq.mean() an very small value in 10^-17
        m2 = d.mean()
        covt = 0
        for j in range(55) :
            t = (stdq[0][j])*(d[0][j] - m2)
            covt = covt + t
        covt = covt/(54)                  #n - 1
        cov[i] = covt

    var = []
    for i in range(8) :
        d = np.array(data[data.columns[i]].values).reshape(1,55)
        var.append(np.var(d))

    cor = np.zeros(8)
    for i in range(8) : 
        cor[i] = cov[i]/np.sqrt(var[i]*np.var(stdq))
    print('Correlation of data : ',cor)
    plt.show()

def QQ_plot(ax,x,xlabel) :
    stdq = []
    x = np.sort(x)
    for i in range(55) :
        stdq.append(stats.norm.ppf((i + 0.5)/55))
    ax.scatter(stdq,x,s=8,label = xlabel)
    ax.set_xlabel('Quantiles')
    ax.legend(loc=2)

    return stdq

def chi2test(ax,npdata,covI,mean) :
    md = np.zeros(55)
    stdq = np.zeros(55)
    for i in range(55) :
        t = np.matrix(npdata[i] - mean)
        md[i] =  t * covI * t.T
        stdq[i] = stats.chi2.ppf(1 - (1 - ((i + 0.5)/55)),8)
    md = np.sort(md)
    ax.scatter(stdq,md,s=10,label = 'chi_square points',color='red')
    ax.set_xlabel = ''
    ax.legend(loc=2)

def corrAndEigen(data) :
    cor = data.corr()
    #print('Correlation : ',cor)

    eig = np.linalg.eig(cor)
    #print('Eigen values  : ',eig[0].T)
    #print('Eigen vectors : ',eig[1].T)

    return cor , eig

def PCA(data,cov,eig) :
    axs = plt.axes()
    #print('Eigen values : ',eig[0])
    n = np.arange(8)
    for i in range(8) :
        axs.plot(n,eig[0])
    axs.scatter(n,eig[0])
    axs.set_title('Scree plot')

    axs.set_ylabel('Eigen values')
    plt.plot()

    eigsum = sum(eig[0])
    l = 0
    count = 1
    for i in range(8) :
        l = l + eig[0][i]
        proportion = (l/eigsum)*100
        print('Proportions : ',proportion)
        if  proportion > 90 :

            break
        else :
            count += 1
    print('Eigen values : ')
    for i in range(count) : 
        print(eig[0][i])

    print('By proportions , k = ',count,' Principal Components\'s')

    print('Principal components (Eigen vectors): ')

    pca = []
    t = eig[1].T
    for i in range(count) : 
        pca.append(t[i])
    print(pca)
    return pca
    
def pcacorrelation(data,mean,cov,corr,pca,eig) :  #corr - cov of normalised data
    npdata = np.matrix(data.values)
    npdata = npdata[:,:-1]

    stddata = np.zeros((8,55))

    for j in range(55) :
        for i in range(8) :
            stddata[i][j] = (npdata[j,i] - mean[i])/np.sqrt(cov[i,i])
    stddata = stddata.T

    stddatacov = np.cov(stddata)
    pcadata = np.zeros((2,55))
    stddata = np.matrix(stddata)
    pca = np.matrix(pca)
    npdata = np.matrix(npdata)
    
    for i in range(2) :
        for j in range(55) : 
            pcadata[i][j] = pca[i]*stddata[j].T
    print('Linear com : ',pcadata)
    
    npdata = npdata.T
    cor = np.zeros((2,8))
    for i in range(2) :
        for j in range(8) :
            cor[i][j] = correlation(npdata[j][:],pcadata[i])
    print('Correlation : ',cor)
    return pcadata , cor

def rank(pca,pcadata,data) :
    t = list()
    countries = data['Country']
    for i in range(len(pcadata[0])) :
        t.append((pcadata[0][i],countries[i]))
    t.sort(reverse=True)

    for i in range(len(t)) :
        if(t[i][1] == 'India') :
            print('India\'s rank :',i+1)
        print('Rank : ',i+1,t[i])

def graphicalanalysis(pca,pcadata) :
    fig,axs = plt.subplots(2,2)  

    scatter(axs[0,0],pcadata[0],pcadata[1],'Scatter plot')
    QQ_plot(axs[1,0],pcadata[0],'pca1')
    stdq = QQ_plot(axs[1,1],pcadata[1],'pca2')

    cov = np.matrix(np.cov(pcadata))
    mean = [pcadata[0].mean(),pcadata[1].mean()]
    chi2test(axs[0,1],pcadata.T,cov.I,np.matrix(mean))
    plt.show()

def scatter(ax,x,y,title):
    ax.scatter(x,y,s = 5)
    ax.set_title(title)

def regression(data) : 
    npdata = data.values
    y = npdata[:,-2:-1]
    y = np.copy(y)

    npdata[:,7:8] = 1
    npdata = npdata[:,:-1]

    npdataT = npdata.T
    t = np.matrix(npdataT @ npdata,dtype=float)
    t = t.I
    temp = npdataT @ y
    beta = t @ temp
    beta = beta -beta%0.001
    print('Beta of linear model : ',beta)

def clusters(data) :
    X = scalingdata(data)

    #Find number of K
    distrotions = []
    K = range(1,10)

    for k in K:
        kmeanModel = KMeans(n_clusters = k).fit(X)
        kmeanModel.fit(X)
        distrotions.append(sum(np.min(cdist(X,kmeanModel.cluster_centers_,'euclidean'),axis = 1))/X.shape[0])

    plt.plot(K,distrotions,'bx-')
    plt.xlabel('k')
    plt.ylabel('Distroriotions')
    print('Distortions :',distrotions)
    plt.show()

    #ploting cluster for k = 4
    km = KMeans(n_clusters = 2,init = 'random',n_init = 10,max_iter=300, tol = 1e-04,random_state = 0)
    y_km = km.fit_predict(X)
    print(y_km)

    #fig,axs = plt.subplots(3,3,figsize = (10,10))
    for i in range(7) : 
        plt.scatter(X[y_km == 0,i],X[y_km == 0,i],s = 50, c='red',edgecolor= 'black',label = 'cluster1')
        plt.scatter(X[y_km == 1,i],X[y_km == 1,i],s = 50, c='green',edgecolor= 'black',label = 'cluster2')
        plt.show()
    #plt.scatter(X[y_km == 2,4],X[y_km == 2,7],s = 50, c='blue',edgecolor= 'black',label = 'cluster3')
    #plt.scatter(X[y_km == 3,4],X[y_km == 3,7],s = 50, c='yellow',edgecolor= 'black',label = 'cluster4')

    plt.scatter(km.cluster_centers_[:,0],km.cluster_centers_[:,1],s = 250,marker='*',c = 'black',edgecolor='black',label='centroids')
    plt.grid()
    plt.show()

    print('Centroids : ',km.cluster_centers_)

def correlation(x,y) :
    v1 = np.var(x)
    y = np.matrix(y)
    v2 = np.var(y)

    if(len(x) != len(y)) :
        print('Wrong data for correlation',len(x),' ',len(y))
        return 0

    cov = covariance(x,y)

    cor = cov/(np.sqrt(v1) * np.sqrt(v2))
    return cor

def covariance(x,y) :
    cov = 0
    for i in range(len(x[0])) :
        cov = cov + (x[0][i] - x.mean())*(( y[0][i] - y.mean()).T)
    cov = cov/55
    return cov

def mean(data) :
    mean = []
    for i in range(len(data.columns) - 1) : 
        mean.append(data[data.columns[i]].values.mean())
    return mean

def scalingdata(data) :
    npdata = data.values
    npdata = npdata[:,:-1]

    npdata = npdata.T
    npdata[0] = 100/npdata[0]
    npdata[1] = 200/npdata[1]
    npdata[2] = 400/npdata[2]
    npdata[3] = 800/((npdata[3]/1)*60+npdata[3]%1)
    npdata[4] = 1500/((npdata[4]/1)*60+npdata[4]%1)
    npdata[5] = 5000/((npdata[5]/1)*60+npdata[5]%1)
    npdata[6] = 10000/((npdata[6]/1)*60+npdata[6]%1)
    npdata[7] = 42000/((npdata[7]/1)*60+npdata[7]%1)
    npdata = npdata.T
    return npdata







'''
    mean = []
    for i in range(len(data.columns) - 1) : 
        mean.append(data[data.columns[i]].values.mean())
    print(mean)
    #print(data['100m(sec)'].values.mean())
    #print(ndata.shape) (55,8)
    #for i in range(len(data.columns) - 1) :
    
    
    ed = []

    for i in range(8) :
        for j in range(55) :
            a = ndata[i][j] - mean[i]
            ed.append(a)
'''