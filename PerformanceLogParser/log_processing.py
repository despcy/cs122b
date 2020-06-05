#!/usr/bin/python
import jsonlines
import sys

def main(file):
    TS=0
    TJ=0
    count=0
    with jsonlines.open(file) as reader:       
        for obj in reader:
            TS=TS+obj["TS"]
            TJ=TJ+obj["TJ"]
            count+=1
    
    TSAVG=TS/(count*1000000)
    TJAVG=TJ/(count*1000000)
    print("Log File: "+file+"         Total Lines:"+str(count)+"         TS average(ms): "+str(TSAVG)+ "       TJ average(ms): "+str(TJAVG))


if __name__ == "__main__":
   main(sys.argv[1])