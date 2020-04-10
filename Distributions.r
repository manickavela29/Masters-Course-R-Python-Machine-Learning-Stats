#Manickavela
#This program compares various Statisticals Distribution by generating them randomly



#Generate random number in dstributions
##Binomial , Poisson , expo, uniform , normal , gamma , weibul
##mean,variance , amqax ,min , mode , median

normdata = rnorm(1000,0,100)
bindata = rbinom(1000,100,0.5)
poisdata = rpois(1000,2)
uformdata = runif(1000,2,8)
expodata = rexp(1000,1.5)
gammadata = rgamma(1000,3,0.5)
weibuldata =rweibull(1000,0.5,1)
betadata = rbeta(1000,2,3)

calculate <- function(data)
{
  print("Mean      : ")
  print(mean(data))
  print("Min       : ")
  print(min(data))
  print("Max       : ")
  print(max(data))
  #print("Mode      : ")
  #print(mode(data))
  print("Median    : ")
  print(median(data))
}

plotting <- function(data,den) 
{
  t <- paste(den,"- Density curve , Histogram")
  hist(data,prob=TRUE,main=t)
  lines(density(data),lty="dotted",col="red")
}

#Normal
calculate(normdata)
plotting(normdata,'Normal')
boxplot(normdata)
#After Removing outliers
outlier = boxplot(normdata,outline = FALSE)["out"]
summary(normdata)

#Binomial
calculate(bindata)
plotting(bindata,'Binomial')
boxplot(bindata)
#After Removing outliers
outlier = boxplot(bindata,outline = FALSE)["out"]
summary(bindata)

#Poisson 
calculate(poisdata)
plotting(poisdata,'Poisson')
boxplot(poisdata)
#After Removing outliers
outlier = boxplot(poisdata,outline = FALSE)["out"]
summary(poisdata)

#Uniform
calculate(uformdata)
plotting(uformdata,'Uniform')
boxplot(uformdata)
#After Removing outliers
outlier = boxplot(uformdata,outline = FALSE)["out"]
summary(uformdata)

#Exponential 
calculate(expodata)
plotting(expodata,'Exponential')
boxplot(expodata)
#After Removing outliers
outlier = boxplot(expodata,outline = FALSE)["out"]
summary(expodata)

#Gamma 
calculate(gammadata)
plotting(gammadata,'Gamma')
boxplot(gammadata)
#After Removing outliers
outlier = boxplot(gammadata,outline = FALSE)["out"]
summary(gammadata)

#Weibul
calculate(weibuldata)
plotting(weibuldata,'Weibul')
boxplot(weibuldata)
#After Removing outliers
outlier = boxplot(weibuldata,outline = FALSE)["out"]
summary(weibuldata)

#Beta
calculate(betadata)
plotting(betadata,'Beta')
boxplot(betadata)
#After Removing outliers
outlier = boxplot(betadata,outline = FALSE)["out"]
summary(betadata)
