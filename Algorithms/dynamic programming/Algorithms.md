```c
// Dynamic Programming example: calculate min num of coins for certain changes
DPChange(money, coins) is

MinNumCoins[0] = 0 
for m from 1 to money:
  MinNumCoins[m] = INF
  for i from 1 to |coins|:
    if m >= coins[i]:
      NumCoins = MinNumCoins[m - coins[i]] + 1
      if NumCoins < MinNumCoins[m]:
        MinNumCoins[m] = NumCoins
return MinNumCoins[money]
```

```c
// Dynamic Programming example: minimum editing distance
EditDistance(A[1...n], B[1...m]) is 

D[i, 0] = i and D[0, j] for all i,j
for j from 1 to m:
  for i from 1 to n:
    insertion  = D[i, j-1] + 1
    deletion = D[i-1, j] + 1
    match = D[i-1, j-1]
    mismatch = D[i-1, j-1] + 1
    if A[i] = B[j]:
      D[i, j] = min(insertion, deletion, match)
    else:
      D[i, j] = min(insertion, deletion, mismatch)
return D[n, m]

// Find the solution using backtracking techniques
OutputAlignment(i, j) is

if i==0 and j==0:
  return
if i>0 and D[i, j] = D[i-1, j] + 1:
  OutputAlignment(i-1, j)
  print A[i] " -" // deletion
else if j>0 and D[i, j] = D[i, j-1] + 1:
  OutputAlignment(i, j-1)
  print "_ " B[j] // insertion
else:
  OutputAlignment(i-1, j-1)
  print A[i] " " B[j] // match or mismatch
```

```c
// Knapsack with item repetitions
Knapsack(W) is

value[0] = 0
for w from 1 to W:
  value[w] = 0
  for i from 1 to n:
    if weight[i] <= w:
      val = value[w - weight[i]] + v[i]
      if val > value[w]:
        value[w] = val
return value[W]
```

```c
// Knapsack without item repetition
Knapsack(W) is

initialize value[0, j] = 0 for all j
initialize value[i, 0] = 0 for all i
for i from 1 to n:
  for w from 1 to W:
    value[i, w] = value[i-1, w]
    if weight[i] <= w:
      val = value[i-1, w-weight[i]] + v[i]
      if val > value[i, w]:
        value[i, w] = val
return value[n, W]
```

```c
// Knapsack with repetition and memoization
Knapsack(w) is

if w is in hash table:
  return value[w]
value[w] = 0
for i from 1 to n:
  if weight[i] <= w:
    val = Knapsack(w - weight[i]) + v[i]
    if val > value[w]:
      value[w] = val
insert value[w] into hash table with key w
return value[w]
```

```c
// Arithmetic Expression Evaluation: di opi ... dj-1 opj-1 dj
// Helper function
MinAndMax(i, j) is 

minval = INF
maxval = -INF
for k from i to j-1:
  a = M[i,k] op[k] M[k+1,j]
  b = M[i,k] op[k] m[k+1,j]
  c = m[i,k] op[k] M[k+1,j]
  d = m[i,k] op[k] m[k+1,j]
  minval = min(minval, a, b, c, d)
  maxval = max(maxval, a, b, c, d)
return (minval, maxval)

// Main method
Parentheses(d1 op1 d2 op2 ... dn) is

for i from 1 to n:
  m[i,i] = d[i]
  M[i,i] = d[i]
for s from 1 to n-1:
  for i from 1 to n-s:
    j = i + s
    m[i,j], M[i,j] = MinAndMax(i,j)
return M[1,n]
```
