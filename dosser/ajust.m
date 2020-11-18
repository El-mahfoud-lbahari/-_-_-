clc;clear all;close all;
% [s,fe]=audioread('timer.mp3');
%  S=s(1:600000,1);
%  S=[S' S'];
% sound(S,fe)
%  audiowrite('attent.wav',S,fe);
x=[zeros(1,50) 2*ones(1,1) zeros(1,50)];
X=fftshift(fft(x));
plot(abs(X))