minimum <- function(m) {
  return(c(min(m), which(m == min(m), arr.ind = TRUE)))
}

# prints
print(minimum(matrix(c(2,4,1,2,1,4,5,6), nrow=2)))
print(minimum(matrix(c(1,4,0,2,1,5,8,6), nrow=4)))