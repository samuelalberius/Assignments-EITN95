%load data from experiments

load('xvalues0.m');
load('xvalues1.m');
load('xvalues2.m');
load('yvalues0.m');
load('yvalues1.m');
load('yvalues2.m');
%normalize in range(0,1)
yvalues0 = yvalues0/sum(yvalues0);
yvalues1 = yvalues1/sum(yvalues1);
yvalues2 = yvalues2/sum(yvalues2);
%convert to minutes
xvalues0 = xvalues0/60;
xvalues1 = xvalues1/60;
xvalues2 = xvalues2/60;
%plot all 3 experiments
figure();
hold on;
plot(xvalues0,yvalues0,'*');
plot(xvalues1,yvalues1,'+');
plot(xvalues2,yvalues2,'o');
ylabel('Frequency');
xlabel('Minutes');
legend('v = 2 m/s','v = 4 m/s','v = U(1,7) m/s');