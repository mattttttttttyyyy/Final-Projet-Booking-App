
Conference Room Management - REST API

Project designed as final project after training. It allows to easily create account for organisation, assign conference rooms and book them.

The tests are written using Testcontainers so working Docker is required to execute them.


## API GUIDE

### Corporations

#### GET all Corporations

```http
  GET /corporation/all
```
Sample output:
```http
[
    {
        "id": 1,
        "conferenceRoomEntityList": [
            {
                "id": 2,
                "name": "Room",
                "bookingEntityList": [
                    {
                        "uniqueId": "S7zS3MK98o",
                        "startTime": "2023-06-14T17:23:00.000+00:00",
                        "endTime": "2023-06-14T18:23:00.000+00:00",
                        "id": 1
                    }
                ],
                "level": 6,
                "sittingNumber": 10,
                "standingNumber": 12
            }
        ],
        "name": "GitHub"
    }
]
```
API returns Long: corporation id.
#### Get a corporation by Id

```http
  GET /corporation/${id}
```
#### Get a corporation by name

```http
  GET /corporation/byName
```
#
#### Add Corporation
```http
  POST /corporation/add
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name:`      | `JSON` | **Required**  |

Sample input:
```http
{
    "name": "GitHub"
}
```
Name must be between 3 and 20 characters.

#### Change corporation name
```http
  PATCH /corporation/update/{id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name:`      | `JSON` | **Required**  |

Sample input:
```http
{
    "name": "GitHub New"
}
```

#### Delete corporation
```http
  DELETE /corporation/delete/{id}
```

### Conference Room

#### GET all rooms

```http
  GET /conferenceRoom/all
```
Sample output:
```http
[
    {
        "id": 2,
        "name": "Room",
        "bookingEntityList": [
            {
                "uniqueId": "S7zS3MK98o",
                "startTime": "2023-06-14T17:23:00.000+00:00",
                "endTime": "2023-06-14T18:23:00.000+00:00",
                "id": 1
            }
        ],
        "level": 6,
        "sittingNumber": 10,
        "standingNumber": 12
    }
]
```

#### Get a rooms by corporation

```http
  GET /conferenceRoom/byCorpoId/${corpotationId}
```

#
#### Add room
```http
  POST /conferenceRoom/add
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `corporate_id`  | `long` | **Parameter** |
| `name:`   | `JSON` | String |
| `level:`   | `JSON` | int 1-10 |
| `sittingNumber:`   | `JSON` | int  |
| `standingNumber:`   | `JSON` | int  |


Sample input:
```http
{
    "name": "Room"
    "level": 6,
    "sittingNumber": 10,
    "standingNumber": 12
}
```
Name must be between 3 and 20 characters.

#### Change room name
```http
  PATCH /conferenceRoom/update/{roomId}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name:`      | `JSON` | **Required**  |

Sample input:
```http
{
    "name": "New room name"
}
```

#### Delete room
```http
  DELETE /conferenceRoom/delete/{id}
```

### Booking

#### GET all bookings

```http
  GET /booking/all
```
Sample output:
```http
[
    {
        "uniqueId": "S7zS3MK98o",
        "startTime": "2023-06-14T17:23:00.000+00:00",
        "endTime": "2023-06-14T18:23:00.000+00:00",
        "id": 1
    }
]
```
#### Get by uniqueId

```http
  GET /booking/byUniqueID/${unique_id}
```

#### Get a booking by room Id

```http
  GET /booking/byRoom/${room_id}
```
#### Check bookings for specific day

```http
  GET /booking/availableToday/{room_id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `timestamp`  | `string` | **Body** |

Example:
```
"2023-06-05T22:00:00.000Z"
```
The time is not considered when search for bookings happens.

Sample output:
```http
[]
```
When no bookings for that day, if there are some, output will be similat to get all.


#### Add booking
```http
  POST /booking/add/{room_id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `startTime`  | `JSON` | Timestamp |
| `endTime:`   | `JSON` | Timestamp |

API returns JSON with uniqueId
```
{
    "uniqueId": "zUxOhKNakw",
    "startTime": "2024-06-14T17:23:00.000+00:00",
    "endTime": "2025-04-15T18:23:00.000+00:00",
    "id": 2
}
```
#### Change booking time
```http
  PATCH /booking/update/{unique_id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `startTime`  | `JSON` | Timestamp |
| `endTime:`   | `JSON` | Timestamp |

#### Delete booking
```http
  DELETE /booking/deleteByID/{id}
```
