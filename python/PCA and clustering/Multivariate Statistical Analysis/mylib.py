import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse
import matplotlib.transforms as transforms
from scipy import stats
from scipy.stats import t
from scipy.stats import f
from scipy.stats import norm

def mean(npdata) :
    l0 = np.shape(npdata)[0]
    l1 = np.shape(npdata)[1]
    mean = np.zeros(l1)
    for i in range(l1) :
        for j in range(l0) : 
            mean[i] = mean[i] + npdata[j][i]
            #print(j)
        mean[i] = mean[i] / l0
    #print('Mean : ',mean)
    return mean

def covariance(npdata,mn = 0) : 
    if(mn == 0) : 
        meanvector = mean(npdata)
    else : 
        meanvector = mn
    l0 = np.shape(npdata)[0]
    l1 = np.shape(npdata)[1]

    covariance = np.random.rand(l1,l1)
    for i in range(l1) :
        for j in range(l1) :
            t = t1 = 0 
            for k in range(l0) :
                t1 = (npdata[k][i] - meanvector[i]) * (npdata[k][j] - meanvector[j])
                t = t + t1
            t = t/(l0 - 1)
            covariance[i][j] = t
    return covariance

def correlation(npdata) : 
    covmatrix = covariance(npdata)

    l1 = np.shape(npdata)[1]

    correlation = np.zeros([l1,l1])

    for i in range(l1) : 
        for j in range(l1) : 
            t = covmatrix[i][j] /( np.sqrt(covmatrix[i][i] * covmatrix[j][j]) )
            correlation[i][j] = t
    return correlation
    #print('correlations ',correlation)

def eigenvalues(npdata) : 

    cov = covariance(npdata)

    eigenval = np.zeros([2])

    a = b = c = 1
    b = (-1)*(cov[0][0] + cov[1][1])
    c = (cov[0][0] * cov[1][1]) - (cov[0][1]*cov[1][0])

    t = np.sqrt(b*b - 4*a*c)

    eigenval[0] = ((-1)*b + t)/(2*a)
    eigenval[1] = ((-1)*b - t)/(2*a)

    eigenval = eigenval.T

    #print('These are eigenvalues ',eigenval)
    return eigenval

def eigenvectors(npdata) : 
    cov = covariance(npdata)
    eigenval = eigenvalues(npdata)
    x = -cov[1][0] / (cov[0][0] - eigenval[0])
    y = 1

    x1 = -cov[1][0] / (cov[0][0] - eigenval[1])
    y1 = 1

    eigenvector = np.array([[x,y],[x1,y1]])

    eigenvector[0][0] = x/np.sqrt(x ** 2 + y ** 2) 
    eigenvector[0][1] = y/np.sqrt(x ** 2 + y ** 2) 

    eigenvector[1][0] = x1/np.sqrt(x1 ** 2 + y1 ** 2) 
    eigenvector[1][1] = y1/np.sqrt(x1 ** 2 + y1 ** 2) 

    #print('Eigen vectors ',eigenvector)
    return eigenvector

def lengthofaxis(npdata,alpha) : 
    eigenvalue = eigenvalues(npdata)
    c = stats.chi2.ppf(1 - alpha,2)
    l1 = np.sqrt(eigenvalue[0]*c)
    l2 = np.sqrt(eigenvalue[1]*c)

    length = np.array([l1,l2])
    return length

def euclidiandistance(npdata) : 
    meanvector = mean(npdata)
    
    edistance = np.zeros(npdata.shape[0],dtype = float)

    for i in range(npdata.shape[0]) :
        a = npdata[i][0] - meanvector[0]
        b = npdata[i][1] - meanvector[1]
        edistance[i] = np.sqrt((a ** 2) + (b ** 2))
    #print('E d id ',edistance)
    return edistance

def mahalonabisdistance(npdata) : 
    meanvector = mean(npdata)
    cov = covariance(npdata)
    
    covI = np.matrix(cov).I
    md = np.zeros(npdata.shape,dtype=float)
    for i in range(npdata.shape[0]) :
        t = npdata[i] - meanvector
        t = np.matrix(t)
        md[i] =  np.sqrt( t * covI * t.T)  
    md = md[:,0]

    #print('Mahalonabis Distance  : ',md)
    return md

def comparingEdMd(npdata,alpha = 0.05) : 
    #Paired t-test
    ed = euclidiandistance(npdata)
    md = mahalonabisdistance(npdata)

    ed = ed.reshape(42,1)
    md = md.reshape(42,1)
    mat = np.hstack((ed,md))

    meanv = mean(mat)
    matcov = covariance(mat)

    a = meanv[0] - meanv[1]
    b = matcov[0][0]/len(ed) + matcov[1][1]/len(md) 
    t = a/np.sqrt(b)
    #print('My cov',matcov)

    df = 2*(42 - 1)
    p = 1 - stats.t.cdf(t,df)

    if(p < alpha) : 
        print('Mahalanobis distance is equal to Euclidian distance ')
    elif meanv[0] > meanv[1] : 
        print('Euclidian distance is greater than Mahalanobis distance ')
    else :
        print('Mahalanobis distance is greater than Euclidian distance ')
    return p

def scatterdiagram(data) : 
    meanv = mean(data.values)
    axes = plt.axes()
    axes.set_xlabel('X1')
    axes.set_ylabel('X2')

    axes.set_title('Scatter Plot')
    
    axes.axvline(meanv[0],color = 'red',lw = 1,linestyle='--',label = 'mean')
    axes.axhline(meanv[1],color = 'red',lw = 1,linestyle='--')

    plt.scatter(data['x1'],data['x2'],s = 10,label = 'Data points')
    axes.legend(loc = 4)
    plt.show()

def QQ_plot(data) : 
    a = np.sort(data['x1'].values)
    b = np.sort(data['x2'].values)

    axes = plt.axes()
    alpha = np.ones(len(a))
    stdq = np.ones(len(a))
    for i in range(len(a)) :
        alpha[i] = (i + 0.5)/len(a)
        stdq[i] = norm.ppf(alpha[i])
    #print('Quantiles ',stdq.T)
    plt.scatter(stdq,a,s=10,label='x1')

    #for b
    alpha = np.ones(len(b))
    stdq = np.ones(len(b))
    for i in range(len(b)) :
        alpha[i] = (i + 0.5)/len(b)
        stdq[i] = norm.ppf(alpha[i])
    #print('Quantiles ',stdq.T)
    plt.scatter(stdq,b,s=10,label='x2')

    axes.set_xlabel('Quantiles')
    axes.set_ylabel('Data points')
    plt.title('Q-Q plot')
    plt.legend(loc=4)
    plt.show()

def chi_square_plot(data) : 
    md = mahalonabisdistance(data.values)
    md = md**2 
    md = np.sort(md)

    stdq = np.ones(len(md))
    for i in range(len(md)) :
        stdq[i] = stats.chi2.ppf(1 - (1 - ((i + 0.5)/len(md))),2)

    plt.scatter(stdq,md,s=10,label = 'chi_square points')
    
    plt.title('Chi-square plot')
    plt.legend(loc=4)
    plt.show()

def contours(data) : 

    meanv = mean(data.values)
    eigval = eigenvalues(data.values)
    eigvect = eigenvectors(data.values)
    p = 2
    n = 42

    theta = np.arctan(eigvect[0][1]/eigvect[0][0])
    theta = np.rad2deg(theta)

    x = np.linspace(-0.2,0.6,100)
    y = np.linspace(-0.2,0.6,100)
    X,Y = np.meshgrid(x,y)

    chi = stats.chi2.isf(0.05,p)          #alpha  0.05

    c = 1/(eigval[0]*(chi))
    d = 1/(eigval[1]*(chi))

    #by ellipse equation simplifying for 2 factors
    m = (X - meanv[0])
    n = (Y - meanv[1])
    F = (d*(((m*np.cos(theta)) - (n*np.sin(theta)))**2)) + (c*(((m*np.sin(theta)) + (n*np.cos(theta)))**2)) - 1
    plt.contour(X,Y,F,[0])


    chi = stats.chi2.isf(0.1,p)          #alpha 0.1

    c = 1/(eigval[0]*(chi))
    d = 1/(eigval[1]*(chi))

    #by ellipse equation simplifying for 2 factors
    m = (X - meanv[0])
    n = (Y - meanv[1])
    F = (d*(((m*np.cos(theta)) - (n*np.sin(theta)))**2)) + (c*(((m*np.sin(theta)) + (n*np.cos(theta)))**2)) - 1
    plt.contour(X,Y,F,[0])


    plt.axvline(meanv[0],lw=1)
    plt.axhline(meanv[1],lw=1)
    plt.scatter(data['x1'],data['x2'],s=10,label='Data points')
    #plt.scatter(meanv[0],meanv[1],color='red')

    plt.title('Confidence Ellipse')
    plt.legend(loc=4)
    plt.show()

def intervals(data) : 
    npdata = data.values
    meanv = mean(npdata)
    cov = covariance(npdata)
    n = 42
    p = 2
    alpha = 0.05

    intervals = dict()

    #T2 interval 
    chi = stats.chi2.isf(alpha,2)
    T2 = []
    for i in range(2) : 
        l = meanv[i] - np.sqrt(chi)*np.sqrt(cov[i][i]/n)
        h = meanv[i] + np.sqrt(chi)*np.sqrt(cov[i][i]/n)
        T2.append([l,h])

    #t - interval
    t2 = []
    z = stats.norm.isf(alpha/2)
    for i in range(2) :
        l = meanv[i] - z*np.sqrt(cov[i][i]/n)
        h = meanv[i] + z*np.sqrt(cov[i][i]/n)
        t2.append([l,h])

    #Bonferroni's intervals
    B = []
    z = stats.norm.isf(alpha/(2 * p))
    for i in range(2) :
        l = meanv[i] - z*np.sqrt(cov[i][i]/n)
        h = meanv[i] + z*np.sqrt(cov[i][i]/n)
        B.append([l,h])
    intervals = {'T2' : T2 , 't2' : t2 , 'B' : B}
    #print('Dict : ',intervals)
    return intervals

def comparing_intervals(data) : 
    intervalsdata = intervals(data)
    #print('Intervals : \n',intervals)
    #--------  Finding length  -------
    #Bonferroni
    Bonferronilength = []
    for i in range(2) :
        t = intervalsdata['B'][i][1] - intervalsdata['B'][i][0]
        Bonferronilength.append(t)  
    
    #T^2
    T2length = []
    for i in range(2) :
        t = intervalsdata['T2'][i][1] - intervalsdata['T2'][i][0]
        T2length.append(t)

    print('\nBonferroni length : \n',Bonferronilength)
    print('\nT^2 length length : \n',T2length)      
    print('\n')

    flag = 1
    for i in range(2) : 
        r = Bonferronilength[i]/T2length[i]
        print('Ratio between Bonferroni and T^2 length : ',r)
        if(r > 1) : 
            flag = 0
    print('\n')
    if(flag == 1) :
        print('Bonferroni interval lengths are less than T^2 ')
    else :
        print('T^2 interval lengths are less then Bonferroni ')

    
    plt.axvline(intervalsdata['B'][0][0],linestyle='--',color='red')
    plt.axvline(intervalsdata['B'][0][1],linestyle='--',color='red')
    plt.axhline(intervalsdata['B'][1][0],linestyle='--',color='red')
    plt.axhline(intervalsdata['B'][1][1],linestyle='--',color='red',label='Bonferroni interval')



    plt.axvline(intervalsdata['T2'][0][0],linestyle='--')
    plt.axvline(intervalsdata['T2'][0][1],linestyle='--')
    plt.axhline(intervalsdata['T2'][1][0],linestyle='--')
    plt.axhline(intervalsdata['T2'][1][1],linestyle='--',label='T^2 interval')


    plt.xlim((0,0.3))
    plt.ylim((0,0.3))
    plt.legend()
    plt.show()

'''

def contours(data) : #Checking the elipse that we drew
    x = np.linspace(-1.0,1.0,100)
    y = np.linspace(-1.0,1.0,100)
    X,Y = np.meshgrid(x,y)
    F = X**2 + Y**2 - 0.6
    plt.contour(X,Y,F,[0])
    plt.axvline(0)
    plt.axhline(0)
    plt.show()

def constructing_ellipse(npdata) :
    ax = plt.axes()
    meanv = mean(npdata)
    data = pd.DataFrame(npdata)

    plt.scatter(data[0],data[1])

    confidense_ellipse(npdata,ax,4.61,facecolor='none',edgecolor="red")
    confidense_ellipse(npdata,ax,5.99,facecolor='none',edgecolor="red")
    
    plt.axvline(meanv[0])
    plt.axhline(meanv[1])
    plt.xlabel('X1')
    plt.ylabel('X2')
    plt.show()

def confidense_ellipse(npdata,ax,alpha,**kwargs) :

    ev = eigenvectors(npdata)

    l = lengthofaxis(npdata,alpha)
    print('Length ',l)
    meanv = mean(npdata)

    ang = np.arctan(ev[0][1]/ev[0][0])
    ang = ang/np.pi*180

    print('Angle ',ang)

    ellipse = Ellipse(meanv,l[0]*2,l[1]*2,ang,**kwargs)
    ax.add_patch(ellipse)
'''