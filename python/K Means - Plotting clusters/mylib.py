from sklearn.cluster import KMeans
from sklearn import metrics
from scipy.spatial.distance import cdist
from scipy import stats
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def Optimumclusters(npdata) :
    #Helps in finding optimum cluster number of K(clusters)
    distrotions = []
    K = range(1,10)
    for k in K:
        kmeanModel = KMeans(n_clusters = k).fit(npdata)
        kmeanModel.fit(npdata)
        distrotions.append(sum(np.min(cdist(npdata,kmeanModel.cluster_centers_,'euclidean'),axis = 1))/npdata.shape[0])
    print('Distortion : ',distrotions)
    plt.plot(K,distrotions,'bx-')
    plt.xlabel('k')
    plt.ylabel('Distroriotions')
    plt.show()

def Dividingtoclusters(npdata) :
    #ploting cluster for k = 4 (from previous function)
    km = KMeans(n_clusters = 4 ,init = 'random',n_init = 10,max_iter=300, tol = 1e-04,random_state = 0)
    y_km = km.fit_predict(npdata)
    #print('Data in clusters : ',y_km)
    plt.scatter(npdata[y_km == 0,0],npdata[y_km == 0,1],s = 50, c='red',edgecolor= 'black',label = 'cluster1')
    plt.scatter(npdata[y_km == 1,0],npdata[y_km == 1,1],s = 50, c='green',edgecolor= 'black',label = 'cluster2')
    plt.scatter(npdata[y_km == 2,0],npdata[y_km == 2,1],s = 50, c='blue',edgecolor= 'black',label = 'cluster3')
    plt.scatter(npdata[y_km == 3,0],npdata[y_km == 3,1],s = 50, c='yellow',edgecolor= 'black',label = 'cluster4')

    plt.scatter(km.cluster_centers_[:,0],km.cluster_centers_[:,1],s = 250,marker='*',c = 'black',edgecolor='black',label='centroids')
    plt.grid()
    plt.show()
    
    #cluster = np.array([[],[],[],[]])
    cluster = [[],[],[],[]]
    for i in range(100) :
        if(y_km[i] == 0) :
            cluster[0].append(npdata[i])
        elif(y_km[i] == 1) :
            cluster[1].append(npdata[i])
        elif(y_km[i] == 2) :
            cluster[2].append(npdata[i])
        elif(y_km[i] == 3) :
            cluster[3].append(npdata[i])
    #print(cluster)

    return cluster,km.cluster_centers_

def Computes(cluster , centroid) :

    meanv = meancalc(cluster)
    c = np.matrix(cluster)
    c = c.T
    cov = np.cov(c)
    cov = np.matrix(cov)
    eig = np.linalg.eig(cov)

    eigvalue = eig[0]
    eigvector = eig[1]
    eigvector = eigvector.T #eigen vectors must be obtained transposing
    return meanv,cov,eigvalue,eigvector

def ConfidenceEllipse(data,clusters,computes) :

    for i in range(len(clusters)) :
        meanv = computes[i][0]
        eigval = computes[i][2]
        eigvect = computes[i][3]
        eigvect = np.matrix(eigvect)
        p = 2
        n = 100
        theta = np.arctan(eigvect[0,1]/eigvect[0,0])
        theta = np.rad2deg(theta)

        x = np.linspace(-6,6,100)
        y = np.linspace(-6,6,100)
        X,Y = np.meshgrid(x,y)

        chi = stats.chi2.isf(0.05,p)          #alpha  0.05
        c = 1/(eigval[0]*(chi))
        d = 1/(eigval[1]*(chi))
        #by ellipse equation simplifying for 2 factors
        m = (X - meanv[0])
        n = (Y - meanv[1])
        F = (d*(((m*np.cos(theta)) - (n*np.sin(theta)))**2)) + (c*(((m*np.sin(theta)) + (n*np.cos(theta)))**2)) - 1
        plt.contour(X,Y,F,[0])

    plt.scatter(data['real'],data['imaginary'],s=10,label='Data points')
    
    plt.title('Confidence Ellipse')
    plt.legend(loc=4)
    plt.show()

def Plotstodetermine(clusters,computes) :
    
    for i in range(len(clusters)) : 
        fig,axs = plt.subplots(1,3)
        meanv = computes[i][0]
        cov = computes[i][1]
        covI = cov.I
        mat = np.matrix(clusters[i])

        QQ_plot(axs[0],mat[:,0],'cluster '+str(i+1))
        QQ_plot(axs[1],mat[:,1],'cluster '+str(i+1))
        Chi2test(axs[2],mat,covI,meanv)
        plt.show()

def Normality_checking(data):
    fig,axs = plt.subplots(1,3)    
    npdata = data.values
    npdata = npdata[:,1:]

    cov = np.cov(npdata.T)
    cov = np.matrix(cov)
    covI = cov.I
    mean = meancalc(npdata)
    QQ_plot(axs[0],data[data.columns[1]].values,data.columns[1])
    QQ_plot(axs[1],data[data.columns[2]].values,data.columns[2])
    Chi2test(axs[2],npdata,covI,mean)

    plt.show()

def Chi2test(ax,cluster,covI,meanv) :
    l = len(cluster)
    md = np.zeros(l)
    stdq = np.zeros(l)

    for i in range(l) :
        t = np.matrix(cluster[i] - meanv)
        md[i] =  t * covI * t.T
        stdq[i] = stats.chi2.ppf(1 - (1 - ((i + 0.5)/l)),2)
    md = np.sort(md)
    ax.scatter(stdq,md,s=10,label = 'chi_square points',color='red')
    ax.set_xlabel = ''
    ax.legend(loc=2)



def QQ_plot(ax,x,xlabel) :
    l = len(x)
    stdq = np.zeros(l)
    data = np.zeros(l)

    for i in range(l) :
        stdq[i] = stats.norm.ppf((i + 0.5)/l)
        data[i] = x[i]

    data = np.sort(data)
    ax.scatter(stdq,data,s=8,label = xlabel)
    ax.set_xlabel('Quantiles')
    ax.legend(loc=2)

    
    m1 = stdq.mean() #nearly zero 10^-17
    m2 = data.mean()
    covt = 0
    for i in range(l) :
        t = (stdq[i] - m1)*(data[i] - m2)
        covt += t
    cov = covt/(l - 1)                  #n - 1

    v1 = np.var(stdq)
    v2 = np.var(data)

    cor = cov/(np.sqrt(v1)*np.sqrt(v2))
    print('Coefficient correlation ',xlabel,': ',cor)

def meancalc(npdata) :
    meanv = []
    l = len(npdata)
    t1= t2 = 0
    for i in range(l) :
        t1 += npdata[i][0]
        t2 += npdata[i][1]
    meanv.append(t1/l)
    meanv.append(t2/l)
    return meanv