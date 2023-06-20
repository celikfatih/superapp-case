# Ozan-SuperApp Case

## Overview   
The system includes three microservices. They are as follows: 

1. `exchange-rate-service`
2. `conversion-service`
3. `conversion-list-service`

`exchange-rate-service`, includes integration with an external API to retrieve exchange rate information.

`conversion-service` is used to convert a certain amount of currency into another type of currency.

`conversion-list-service` used to get to the list of conversions made using `conversion-service`.

## Getting Started

To run locally, follow the steps below.

### Prerequisites

To run locally, you must first have `docker` and `docker-compose`.

- [Here's](https://docs.docker.com/desktop/) how to download Docker to your local environment.
- [Here's](https://docs.docker.com/compose/install/) how to download Docker Compose to your local environment.

### Installation

1. First of all, it should be checked that `docker` and `docker-compose` are running.

2. Clone the repo:
   ```sh
   git clone https://github.com/celikfatih/superapp-case.git
    ```
3. Specify the values of the parameters in the `.env` file. If you do not specify, the `.env` file will be used directly.

4. Inside the project, to start library project (The `-d` parameter optional and allows it to run in the background):
   ```sh
   docker-compose up -d
   ```

5. Each microservice has `Swagger` containing its own API documentation. If you don't get any error the following 
address can be used to access APIs and documentation:

    ### exchange-rate-service
    ```
    http://localhost:8082/swagger.html
    ```
    
    ### conversion-service
    ```
    http://localhost:8083/swagger.html
    ```
    
    ### conversion-list-service
    ```
    http://localhost:8084/swagger.html
    ```

## Contact

[Fatih Celik](mailto:celikfatih@protonmail.com)

Project Link: [https://github.com/celikfatih/superapp-case/](https://github.com/celikfatih/superapp-case/)

