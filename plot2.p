set grid
set term jpeg medium size 1024,768


set output "pos00.jpg"
plot "pos00.data" u 1:2 ti "+", "pos00.data" u 3:4 ti "-"

set output "pos01.jpg"
plot "pos01.data" u 1:2 ti "+", "pos01.data" u 3:4 ti "-"


set output "relaxT.jpg"
plot "relaxation.data" u 1:2 ti "T" w l
set output "relaxK.jpg"
plot "relaxation.data" u 1:3 ti "K" w l
set output "relaxV.jpg"
plot "relaxation.data" u 1:4 ti "V" w l
set output "relaxEtot.jpg"
plot "relaxation.data" u 1:5 ti "Etot" w l
set output "relaxAlpha.jpg"
plot "relaxation.data" u 1:6 ti "alpha" w l



set output "meanTimeEnergy.jpg"
plot "md2a-means.data" u 1:3 ti "meanE" w l

set output "meanTimeD.jpg"
plot "md2a-means.data" u 1:5 ti "D" w l

set output "meanTimeOutOfCluster.jpg"
plot "md2a-means.data" u 1:6 ti "out of cluster" w l

set output "meanTimeReticularStep.jpg"
plot "md2a-means.data" u 1:7 ti "reticular step" w l



set output "instT.jpg"
plot "md2a-inst.data" u 1:2 ti "T" w l
set output "instK.jpg"
plot "md2a-inst.data" u 1:3 ti "K" w l
set output "instV.jpg"
plot "md2a-inst.data" u 1:4 ti "V" w l
set output "instEtot.jpg"
plot "md2a-inst.data" u 1:5 ti "Etot" w l




set output "pos02.jpg"
plot "pos02.data" u 1:2 ti "+", "pos02.data" u 3:4 ti "-"
set output "pos03.jpg"
plot "pos03.data" u 1:2 ti "+", "pos03.data" u 3:4 ti "-"

set output "pos00.00.jpg"
plot "pos00.0.data" u 1:2 ti "+", "pos00.0.data" u 3:4 ti "-"
set output "pos00.01.jpg"
plot "pos00.1.data" u 1:2 ti "+", "pos00.1.data" u 3:4 ti "-"
set output "pos00.02.jpg"
plot "pos00.2.data" u 1:2 ti "+", "pos00.2.data" u 3:4 ti "-"
set output "pos00.03.jpg"
plot "pos00.3.data" u 1:2 ti "+", "pos00.3.data" u 3:4 ti "-"
set output "pos00.04.jpg"
plot "pos00.4.data" u 1:2 ti "+", "pos00.4.data" u 3:4 ti "-"
set output "pos00.05.jpg"
plot "pos00.5.data" u 1:2 ti "+", "pos00.5.data" u 3:4 ti "-"
set output "pos00.06.jpg"
plot "pos00.6.data" u 1:2 ti "+", "pos00.6.data" u 3:4 ti "-"
set output "pos00.07.jpg"
plot "pos00.7.data" u 1:2 ti "+", "pos00.7.data" u 3:4 ti "-"
set output "pos00.08.jpg"
plot "pos00.8.data" u 1:2 ti "+", "pos00.8.data" u 3:4 ti "-"
set output "pos00.09.jpg"
plot "pos00.9.data" u 1:2 ti "+", "pos00.9.data" u 3:4 ti "-"
set output "pos00.10.jpg"
plot "pos00.10.data" u 1:2 ti "+", "pos00.10.data" u 3:4 ti "-"
set output "pos00.11.jpg"
plot "pos00.11.data" u 1:2 ti "+", "pos00.11.data" u 3:4 ti "-"
set output "pos00.12.jpg"
plot "pos00.12.data" u 1:2 ti "+", "pos00.12.data" u 3:4 ti "-"
set output "pos00.13.jpg"
plot "pos00.13.data" u 1:2 ti "+", "pos00.13.data" u 3:4 ti "-"
set output "pos00.14.jpg"
plot "pos00.14.data" u 1:2 ti "+", "pos00.14.data" u 3:4 ti "-"
set output "pos00.15.jpg"
plot "pos00.15.data" u 1:2 ti "+", "pos00.15.data" u 3:4 ti "-"
set output "pos00.16.jpg"
plot "pos00.16.data" u 1:2 ti "+", "pos00.16.data" u 3:4 ti "-"
set output "pos00.17.jpg"
plot "pos00.17.data" u 1:2 ti "+", "pos00.17.data" u 3:4 ti "-"
set output "pos00.18.jpg"
plot "pos00.18.data" u 1:2 ti "+", "pos00.18.data" u 3:4 ti "-"
set output "pos00.19.jpg"
plot "pos00.19.data" u 1:2 ti "+", "pos00.19.data" u 3:4 ti "-"


