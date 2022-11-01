These data files are set OR-Library in Guastaroba G. and Speranza M.G. A Heuristic for BILP Problems: The Single Source Capacitated Facility Location Problem, currently under review in the European Journal of Operational Research.

This is a subset of the instances belonging to the OR-Library and originally proposed for the Multi-Source Capacitated Facility Location Problem, that is publicly available at http://people.brunel.ac.uk/~mastjjb/jeb/orlib/capinfo.

There are currently 36 data files. The format of these data files is:

|J| |I|
s_1 f_1
s_2 f_2
  ...
s_|J| f_|J|
d_1 d_2 d_3  d_|I|
c_{11} c_{12} c_{13}  c_{1|I|}
c_{21} c_{22} c_{23}  c_{2|I|}
   ...    ...    ...   ....
c_{|J|1} c_{|J|2} c_{|J|3}  c_{|J||I|}
OPT_VAL

where:

|J| is the number of potential facility locations;
|I| is the number of customers;
s_j (j=1,...,|J|) is the capacity of facility j;
f_j (j=1,...,|J|) is the fixed cost of opening facility j;
d_i (i=1,...,|I|) is the demand of customer i;
c_{ji} (j=1,...,|J|), (i=1,...,|I|) is the cost of allocating all the demand of customer i to facility j;
OPT_VAL is the optimum solution value.