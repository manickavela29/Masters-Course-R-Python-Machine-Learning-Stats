import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt

def frequency(data):
    freq = {}
    for i in data:
        if(i in freq.keys()):
            freq[i]+=1
        else:
            freq[i]=1
    return list(freq.keys()),list(freq.values())

def maximum(data):  
    return data[-1]

def minimum(data):  
    return data[0]

def iqr(data):
    q1 = int(len(data)/4)
    q3 = int(3*len(data)/4)
    return data[q3]-data[q1] 

def mean(data): 
    sum = 0
    for i in data:
        sum+=i
    avg = sum/len(data)
    return avg

def median(data):  
    size = len(data)
    if(size%2 == 1):
        med = data[int(size/2)]
    else:
        med = (data[int(size/2)] + data[int(size/2)-1])/2
    return med

def std_dev(data,avg):  
    vari = var(data,avg)  
    return np.sqrt(vari)

def var(data,avg):  
    diff = 0
    for i in data:
        diff += (i-avg)**2
    vari = diff/(len(data)-1)  
    return vari

def dotplot(data,ax):  ##attaches scatterplot of data  as dotplot to the axes
    ax.set_title("Dot Plot")
    x,y = frequency(data)
    for i in range(len(x)):
        for j in range(y[i]):
            ax.scatter(x[i],j/3,color='green',s=5)

def histogram(data,ax):  ##attaches histogram of the data to the axes
    ax.hist(data,bins=25)
    ax.set_title("Histogram")



def boxplot(data,ax):  ##returns boxplot dictionary
    ax.set_title("Box Plot")
    return(ax.boxplot(data,vert = True))

def qq_plot(data,ax):  ##attaches qq_plot to the axes
    q = []
    for i in range(len(data)):
        q.append(stats.norm.ppf((i+0.5)/len(data)))
    ax.scatter(data,q,s=10)
    ax.set_title("Q-Q Plot")

def densitycurve(data,ax,dist='') :
    data = np.sort(data)
    ax.set_title("Density Curve")
    if dist == 'b' :
        ax.plot(data,stats.binom.pmf(data,100,0.5))
    elif dist == 'p' :
        ax.plot(data,stats.poisson.pmf(data,3))
    elif dist == 'e' :
        ax.plot(data,stats.expon.pdf(data,3))
    elif dist == 'n' :
        ax.plot(data,stats.norm.pdf(data,5,1))
    elif dist == 'sn' :
        ax.plot(data,stats.norm.pdf(data,0,1))
    elif dist == 'u' :
        ax.plot(data,stats.uniform.pdf(data,1,5))
