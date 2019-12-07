f <- function(vec, pattern, how_much) {
  res <- c()
  patterns_idx <- which(vec == pattern)
  window <- (how_much - 1)
  print(patterns_idx)
  for (i in 1:(length(patterns_idx) - window)) {
    nxt <- (i + window)
    if ((patterns_idx[nxt] - patterns_idx[i]) == window) {
      res <- append(res, patterns_idx[i])
    }
  }
  return(res)
}

print(f(c(1,1,2,3,2,2,4,2,2,2), 2, 2))
print(f(c(1,1,2,3,2,2,4,2,2,2), 2, 3))
print(f(c(1,1,2,3,2,2,4,2,2,2,4,4,4,4), 4, 4))