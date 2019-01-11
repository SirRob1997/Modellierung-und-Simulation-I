
import numpy as np
import matplotlib.pyplot as plt

### 8.1.4 ###

n = 4
w = np.array([1, 0.5, 0.5, 0.25])
p = [0.3, 0.7, 0.9]
a = lambda p, n: np.array([np.prod(w[:i+1]*p*n) for i in range(len(w))])
y = np.array([a(q, n) for q in p])
x = np.zeros((len(p), n+1))
x[:, 0] = 1/(1 + np.sum(y, axis=1))
for i in range(n):
	x[:, i+1] = x[:, 0] * y[:, i]

#for i in range(3):
#	plt.plot(np.arange(n+1), x[i, :], label="p={}".format(p[i]))
#plt.legend()
#plt.xlabel("states")
#plt.ylabel("probabilities")
#plt.xticks(np.arange(n+1))
#plt.savefig("state_probs.png")
#plt.show()

### 8.1.5/8.1.6 ###

p_b = (x[:, 4]/2)/(x[:, 0] + x[:, 1] + x[:, 2] + x[:, 3] + x[:, 4]/2)
print(p_b)
p_w = (x[:, 3] + x[:, 4])/(x[:, 0] + x[:, 1] + x[:, 2] + x[:, 3] + x[:, 4])
print(p_w)
"""
"""
### 8.1.7 ###

steps = 100
rho = np.linspace(0, 1, steps)
y = np.array([a(q, n) for q in rho])
xrho = np.zeros((len(rho), n+1))
xrho[:, 0] = 1/(1 + np.sum(y, axis=1))
for i in range(n):
	xrho[:, i+1] = xrho[:, 0] * y[:, i]
p_brho = (xrho[:, 4]/2)/(xrho[:, 0] + xrho[:, 1] + xrho[:, 2] + xrho[:, 3] + xrho[:, 4]/2)
p_wrho = (xrho[:, 3] + xrho[:, 4])/(xrho[:, 0] + xrho[:, 1] + xrho[:, 2] + xrho[:, 3] + xrho[:, 4])
#plt.plot(rho, p_brho, label="p_b")
#plt.plot(rho, p_wrho, label="p_w")
#plt.legend()
#plt.xlabel("rho")
#plt.ylabel("probabilities")
#plt.savefig("block_and_wait.png")
#plt.show()

### 8.1.8 ###

eq = x[:, 3] + 2 * x[:, 4]
print(eq)
es = x[:, 1] + 2 * (x[:, 2] + x[:, 3] + x[:, 4])
print(es)

### 8.1.9 ###

eqrho = xrho[:, 3] + 2 * xrho[:, 4]
esrho = xrho[:, 1] + 2 * (xrho[:, 2] + xrho[:, 3] + xrho[:, 4])
plt.plot(rho, eqrho, label="E(Q)")
plt.plot(rho, esrho, label="E(S)")
plt.legend()
plt.xlabel("rho")
plt.ylabel("multiple of (1 over mu)")
plt.savefig("queue_and_server.png")
plt.show()
