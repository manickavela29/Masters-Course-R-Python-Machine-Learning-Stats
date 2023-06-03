import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats
import mylib as mylib

#Generating random data and truncating them 
binomial = np.random.binomial(100,0.5,1000)
exponential = np.random.exponential(3,1000)
exponential -= exponential%1  
normal = np.random.normal(5,1,1000)
normal -= normal%0.05 
poisson = np.random.poisson(3,1000)
std_nrm = np.random.normal(0,1,1000)
std_nrm -= std_nrm%0.05  
uniform = np.random.uniform(1,5,1000)
uniform -= uniform%0.05 

#---------Binomial--------------
binomial = np.sort(binomial)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------BINOMIAL------------")
print("Maximum            :",mylib.maximum(binomial))
print("Minimum            :",mylib.minimum(binomial))
print("Mean               :",mylib.mean(binomial))
print("Median             :",mylib.median(binomial))
print("Variance           :",mylib.var(binomial,mylib.mean(binomial)))
print("Standard Deviation :",mylib.std_dev(binomial,mylib.mean(binomial)))
print("IQR                :",mylib.iqr(binomial))
mylib.dotplot(binomial,axs[0,0])
mylib.histogram(binomial,axs[0,1])
out = mylib.boxplot(binomial,axs[1,0])
mylib.qq_plot(binomial,axs[1,1])
mylib.densitycurve(binomial,axs[0,2],'b')
outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)

plt.show()

#------------Exponential------------------
exponential = np.sort(exponential)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------EXPONENTIAL------------")
print("Maximum            :",mylib.maximum(exponential))
print("Minimum            :",mylib.minimum(exponential))
print("Mean               :",mylib.mean(exponential))
print("Median             :",mylib.median(exponential))
print("Variance           :",mylib.var(exponential,mylib.mean(exponential)))
print("Standard Deviation :",mylib.std_dev(exponential,mylib.mean(exponential)))
print("IQR                :",mylib.iqr(exponential))
mylib.dotplot(exponential,axs[0,0])
mylib.histogram(exponential,axs[0,1])
mylib.densitycurve(exponential,axs[0,2],'e')
out = mylib.boxplot(exponential,axs[1,0])
mylib.qq_plot(exponential,axs[1,1])

outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)
plt.show()

#---------Normal--------------
normal = np.sort(normal)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------NORMAL------------")
print("Maximum            :",mylib.maximum(normal))
print("Minimum            :",mylib.minimum(normal))
print("Mean               :",mylib.mean(normal))
print("Median             :",mylib.median(normal))
print("Variance           :",mylib.var(normal,mylib.mean(normal)))
print("Standard Deviation :",mylib.std_dev(normal,mylib.mean(normal)))
print("IQR                :",mylib.iqr(normal))
mylib.dotplot(normal,axs[0,0])
mylib.histogram(normal,axs[0,1])
mylib.densitycurve(normal,axs[0,2],'n')
out = mylib.boxplot(normal,axs[1,0])
mylib.qq_plot(normal,axs[1,1])

outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)

plt.show()

#---------Poisson--------------
poisson = np.sort(poisson)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------POISSON------------")
print("Maximum            :",mylib.maximum(poisson))
print("Minimum            :",mylib.minimum(poisson))
print("Mean               :",mylib.mean(poisson))
print("Median             :",mylib.median(poisson))
print("Variance           :",mylib.var(poisson,mylib.mean(poisson)))
print("Standard Deviation :",mylib.std_dev(poisson,mylib.mean(poisson)))
print("IQR                :",mylib.iqr(poisson))
mylib.dotplot(poisson,axs[0,0])
mylib.histogram(poisson,axs[0,1])
out = mylib.boxplot(poisson,axs[1,0])
mylib.qq_plot(poisson,axs[1,1])
mylib.densitycurve(poisson,axs[0,2],'p')

outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)

plt.show()

#---------Standard Normal--------------
std_nrm = np.sort(std_nrm)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------STANDARD NORMAL------------")
print("Maximum            :",mylib.maximum(std_nrm))
print("Minimum            :",mylib.minimum(std_nrm))
print("Mean               :",mylib.mean(std_nrm))
print("Median             :",mylib.median(std_nrm))
print("Variance           :",mylib.var(std_nrm,mylib.mean(std_nrm)))
print("Standard Deviation :",mylib.std_dev(std_nrm,mylib.mean(std_nrm)))
print("IQR                :",mylib.iqr(std_nrm))
mylib.dotplot(std_nrm,axs[0,0])
mylib.histogram(std_nrm,axs[0,1])
mylib.densitycurve(std_nrm,axs[0,2],'sn')
out = mylib.boxplot(std_nrm,axs[1,0])
mylib.qq_plot(std_nrm,axs[1,1])

outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)

plt.show()

#---------Uniform--------------
uniform = np.sort(uniform)
fig,axs = plt.subplots(2,3,figsize = (10,10))
print("-----------UNIFORM------------")
print("Maximum            :",mylib.maximum(uniform))
print("Minimum            :",mylib.minimum(uniform))
print("Mean               :",mylib.mean(uniform))
print("Median             :",mylib.median(uniform))
print("Variance           :",mylib.var(uniform,mylib.mean(uniform)))
print("Standard Deviation :",mylib.std_dev(uniform,mylib.mean(uniform)))
print("IQR                :",mylib.iqr(uniform))
mylib.dotplot(uniform,axs[0,0])
mylib.histogram(uniform,axs[0,1])
mylib.densitycurve(uniform,axs[0,2],'u')
out = mylib.boxplot(uniform,axs[1,0])
mylib.qq_plot(uniform,axs[1,1])

outliers = list(item.get_ydata() for item in out['fliers'])
outliers = list(outliers[0])
outliers,y = mylib.frequency(outliers)
print("Outliers           :",outliers)
plt.show()
