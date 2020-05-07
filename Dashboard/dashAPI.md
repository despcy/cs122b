# Dashboard API:

## Authentification:

use the same frontend as user login

### request:

```http
POST /api/dash/login

email=classta@email.edu&password=classta
```

### Response:

```json
{
   "message":0,
   "data": "user Object"
}
```


## DashInfo:

### request:

```http
 GET /api/dash/show
```

### response:

```json
{
   "message":0,
   "data": {
       "admin": "classta",
       "tables":[
           {
            "name":"movies",
            "attr":[
                {
                    "name":"title",
                    "type":"VARCHAR(10)"
                },
                {
                    "name":"year",
                    "type":"INT"                    
                }
            ]

           },
           {
            "name":"sales",
            "attr":[
                {
                    "name":"movieId",
                    "type":"VARCHAR(10)"
                },
                {
                    "name":"id",
                    "type":"INT"                    
                }
            ]
           }
       ]
   }
}
```

## Add Movie

### request:

```http
POST /api/dash/addMovie

title=tt&year=2002&director=dir&star=sta&genre=gen
```

### response:

```json
{
   "message":0,
   "data": "success"
}
```

## insert Star

### request:

```http
POST /api/dash/addStar

name=nsdf&birth=2002-10-20
```

### response:

```json
{
   "message":0,
   "data": "success"
}
```

## Message:

**message: 0 for success, -1 for auth fail**