same([ ],[ ]).   
same([H1|R1], [H2|R2]):-
    H1 == H2,
    same(R1, R2).
