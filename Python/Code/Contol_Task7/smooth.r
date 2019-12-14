smooth <- function(x) {
  m <- mean(x)
  return (ifelse(x > m * 3, m, x))
}

print(smooth(c(1, 2, 3, 4, 25, 8, 12, 40, 3, 2, 1, 80)))
print(smooth(c(2,4,2,1,6,7,2,20,3,5,2,45,6)))
print(smooth(c(1,1,1,1,1,1,70,320)))
print(smooth(c(5,4,3,2,2,5,6,7,8)))