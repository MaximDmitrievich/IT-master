f <- function(vec, pattern, how_much) {
  res <- c()
  patterns_idx <- which(vec == pattern)
  for (i in 1:(length(patterns_idx) - 1)) {
    if (patterns_idx[i + 1] - patterns_idx[i] == how_much - 1) {
      res <- append(res, patterns_idx[i])
    }
  }
  return(res)
}

print(f(c(1,1,2,3,2,2,4,2,2,2), 2, 2))