# Api

## Basic format:

message: 0->success
message: 1->error
message: -1->auth fail
```json
{
   "message":0,
   "data": "Return data field are here.."
}

```

## Data field:

### Login:

最后再做，不影响

### Search/List:

#### request:

**sort:** title/rating
**order:** asc/desc


Search movie: with substring matching:
``/api/search?title=t&year=year&director=d&star=s&page=1&pagesize=20&sort=title&order=asc``


List movies in a genre:

``/api/list?genre=gen&page=1&pagesize=20&sort=title&order=asc``

List movies start with alphabet:

alphabet is (0,1,2,3..A,B,C...X,Y,Z) or * to list all

``/api/list?alphabet=a&page=1&pagesize=20&sort=title&order=asc``

#### response:

```json
{
   "message":0,
   "data":{
      "totalItem":100,
      "curPage":1,
      "pagesize":20,
      "movies":[
         {
            "id":"mm1",
            "title":"this is title",
            "year":2002,
            "director":"No. 189, Grove St, Los Angeles",
            "genres":[
               {
                  "id":"mm1",
                  "name":"gen1"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               }
            ],
            "stars":[
               {
                  "id":"mm1",
                  "name":"star1"
               },
               {
                  "id":"mm2",
                  "name":"star2"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               }
            ],
            "rating":3.8
         },
         {
            "id":"mm2",
            "title":"this is title",
            "year":2002,
            "director":"No. 189, Grove St, Los Angeles",
            "genres":[
               {
                  "id":"mm1",
                  "name":"gen1"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               }
            ],
            "stars":[
               {
                  "id":"mm1",
                  "name":"star1"
               },
               {
                  "id":"mm2",
                  "name":"star2"
               },
               {
                  "id":"mm2",
                  "name":"gen2"
               }
            ],
            "rating":3.8
         }
      ]
   }
}

```

---------

#### request:

List all movie genre sort alphabetical:
``/api/genres``

#### response:

```json
{
    "message":0,
    "data":[
        "gen1",
        "gen2",
        "gen3"
    ]
}

```


------------

#### request:

``/api/movie?movieId=mm123``

#### response:

```json
{
   "message":0,
   "data":{
      "id":"1234",
      "title":"titleeee",
      "year":2012,
      "director":"ycx",
      "rating":3.4,
      "stars":[
         {
            "id":"s4",
            "name":"fff"
         },
         {
            "id":"s5",
            "name":"ffdf"
         }
      ],
      "genres":[
         "gen1",
         "gen2",
         "gen3",
         "gen4"
      ]
   }
}
``` 

--------

#### request:

``/api/star?starId=st1234``

#### response:

```json
{
   "message":0,
   "data":{
      "id":"2233",
      "name":"hahahha",
      "birthYear":2020,
      "movies":[
         {
            "id":"2233",
            "name":"mov1"
         },
         {
            "id":"3344",
            "name":"mov2"
         }
      ]
   }
}

```





## Shopping Cart/User


#### request:


Add user cart:
```
POST /api/cart/add

BODY:
movieId=mid&movieTitle=title
```

Update user cart: 

```http
POST /api/cart/update

Header:
content-type: application/json

BODY:

{
   "data":[
      {
         "movieId":"m123",
         "movieTitle":"haha",
         "quantity":2,
         "price":344
      },
      {
         "movieId":"m1213",
         "movieTitle":"hafaha",
         "quantity":2,
         "price":3454
      }
   ]
}


```

Get user cart:

``/api/cart/show``


#### response:

```json
{
   "message":0,
   "data":[
      {
         "movieId":"m123",
         "movieTitle":"haha",
         "quantity":2,
         "price":344
      },
      {
         "movieId":"m1213",
         "movieTitle":"hafaha",
         "quantity":2,
         "price":3454
      }
   ]
}

```

--------

#### request:

checkout:

```http

POST /api/cart/checkout

BODY:

first=chenxi&last=yang&number=1231234645541652&expire=0520


```

#### response:

```json
{
   "message":0,
   "data":"success/fail"
}
```