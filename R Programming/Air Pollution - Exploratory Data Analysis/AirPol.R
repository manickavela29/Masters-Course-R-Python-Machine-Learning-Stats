library(ggplot2)
library(dplyr)


data = read.csv('air_pollution.csv')
mtcars
View(attitude)
View(data)

#Converting to factors

data$state = as.factor(data$state)
data$location = as.factor(data$location)
data$agency = as.factor(data$agency)
data$location_monitoring_station = as.factor(data$location_monitoring_station)

t <- data[data$state == 'Tamil Nadu',] 
summary(data)
summary(t)

#t <- data %>% filter()


#INDIA
ggplot(t,aes(x=t$so2),) +
  scale_x_continuous(limits = c(0,50)) +
  geom_histogram(na.rm = TRUE,binwidth = 1)


t <- summary(data$state)

unique(data$state)

#tamilnadu <- subset(data,select = c(data$so2,data$no2) , data$state == "Tamil Nadu")
tamilnadu <- data %>%
          filter(data$state %in% c("Tamil Nadu")) %>%
          group_by(location) %>% 
          summarise(
            mean_so = mean(so2,na.rm = TRUE),
            mean_no = mean(no2,na.rm = TRUE),
            count_data = n()
          )

#SO2 level
ggplot(tamilnadu , aes(x = tamilnadu$location, y = tamilnadu$mean_so , fill =  tamilnadu$location)) +
    geom_bar(stat = "identity")+
    coord_flip() +
    theme_classic()
  
#NO2 level
ggplot(tamilnadu , aes(x = tamilnadu$location, y =tamilnadu$mean_no , fill =  tamilnadu$location)) +
  geom_bar(stat = "identity", position = "dodge")+
  coord_flip() +
  theme_classic()

#Data Cleaning 

  #Correcting state names
tamilnadu$location = as.character((tamilnadu$location))
tamilnadu$location = ifelse((tamilnadu$location == "Turicorin") ,
                            "Tuticorin" ,
                            tamilnadu$location)
tamilnadu <- tamilnadu %>%
    group_by(location) %>% 
    summarise(
      mean_so = mean(mean_so,na.rm = TRUE),
      mean_no = mean(mean_no,na.rm = TRUE),
      count_data = n()
    )


ggplot(tamilnadu , aes(x = tamilnadu$location, y = tamilnadu$mean_so , fill =  tamilnadu$location)) +
  geom_bar(stat = "identity")+
  coord_flip() +
  theme_classic()

india <- data %>%
        group_by(state) %>%
        summarise(
          mean_so = mean(so2,na.rm = TRUE),
          mean_no = mean(no2,na.rm = TRUE),
          mean_rspm = mean(rspm,na.rm = TRUE),
          mean_spm = mean(spm,na.rm = TRUE),
          count_data = n()
        )
india = india[order(india$mean_so,decreasing = FALSE),]

ggplot(india , aes(x = india$state, y = india$mean_so , fill = india$state)) +
  geom_bar(stat = "identity")+
  coord_flip() +
  theme_classic()

india = india[order(india$mean_no,decreasing = FALSE),]

ggplot(india , aes(x = india$state, y = india$mean_no , fill = india$state)) +
  geom_bar(stat = "identity")+
  coord_flip() +
  theme_classic()

avgspm <- data %>%
          group_by(state) %>%
          summarise(
            mean_spm = mean(spm,na.rm = TRUE)
          )

summary(data$so2)

#Removing 'NA'
t_so2 = ifelse(is.na(data$so2),
                  ave(data$so2 , FUN = function(x) mean(x,na.rm = TRUE)),
                  data$so2)

t_no2 = ifelse(is.na(data$no2),
                  ave(data$no2 , FUN = function(x) mean(x,na.rm = TRUE)),
                  data$no2)
View(t_so2[])
summary(t_so2[])




```{r}
#plotting the visualisation
# 1 - rating vs complaints
ggplot(newdata,aes(rating,complaints,color=rating)) +
  geom_point()+
  geom_abline(intercept = bestmodel$coefficients[1] , slope = bestmodel$coefficients[2] ) +
  theme_classic()

# 2 - rating vs learning
ggplot(newdata,aes(rating,learning,color=rating)) +
  geom_point()+
  geom_abline(intercept = bestmodel$coefficients[1] , slope = bestmodel$coefficients[3] ) +
  theme_classic()
```


```{r}
ggplot(newdata, aes(complaints)) +
  geom_point(aes(y = rating)) +
  geom_line(aes(y = bestmodel$fitted.values),colour = "red",size = 1)

ggplot(newdata, aes(learning)) +
  geom_point(aes(y = rating)) +
  geom_line(aes(y = bestmodel$fitted.values),colour = "red",size = 1)
```

/*
  It can be oberved that rating is completely dependent on 2 factors
1- complaints  - How well the organisation took care of the complaints by the employees
2- Learnings   - How the organisation facilitated emplyees learning intrest

Compared to the other factors these 2 played a major role in making the employees by good overall rating
But other factors too contributed in ununiform means , there by making them have various degree of variance.
That is the reason the best model is havin only 73% accuracy(approx)
  
  */