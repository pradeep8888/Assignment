# RetailerRewardsAssignment
<h2>Project Desciption:</h2>
                             &nbsp This project assignment is related to calculate customer rewards points based on transactions happened from customer based on doller spent in every transctions.
              also Points give based on some criteria and that criteria is on a record of every transaction during a three month period. We are calculating the points on monthly
              basis on given criteria.<br>
                <b>Criteria is:</b><br>
                  -2 points for every dollar spent over $100 in each transaction<br>
                  -plus 1 point for every dollar spent between $50 and $100 in each transaction.<br> 
                  {e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points}

                  <b>APIs Written by pradeep.andhare</b>
#Post Request		  
1>In this API Adding the data into database for temporary purpose  
URL -> http://localhost:8080/addData/
<br>
#sample data
```
        new Transcations(14l, "PraveenK", LocalDate.of(2024, 7, 5), 70),
				new Transcations(14l, "PraveenK", LocalDate.of(2024, 6, 25), 70),
				new Transcations(11l, "PradeepA", LocalDate.of(2024, 5, 15), 120),
				new Transcations(12l, "PrashantJ", LocalDate.of(2024, 6, 10), 159),
				new Transcations(13l, "AvinashM", LocalDate.of(2024, 5, 25), 130),
				new Transcations(14l, "PraveenK", LocalDate.of(2024, 5, 15), 120));
```
#GET Request
2> In this API fetching the specific Customer details with calculated earned points based on monthly basis<br>
URL -> http://localhost:8080/getCustomer/PraveenK
<br>
Input:need to call get API with argument->"PraveenK" (which is a name of customer based on that it will fetch information of that customer)<br>
Output:
```{
    "customerName": "PraveenK ",
    "monthlyPoints": {
        "June": 20,
        "May": 90,
        "July": 20
    }
}
```
#GET Request
3>In this API we are fetching all customer details which can return calculated earned points on monthly basis<br>
URL -> http://localhost:8080/getAllCustomer
Input: need to call get API without argument
Output:
```
[
    {
        "customerName": "PraveenK",
        "monthlyPoints": {
            "July": 20
        },
        "total": 20
    },
    {
        "customerName": "PraveenK",
        "monthlyPoints": {
            "June": 20
        },
        "total": 20
    },
    {
        "customerName": "PradeepA",
        "monthlyPoints": {
            "May": 90
        },
        "total": 90
    },
    {
        "customerName": "PrashantJ",
        "monthlyPoints": {
            "June": 168
        },
        "total": 168
    },
    {
        "customerName": "AvinashM",
        "monthlyPoints": {
            "May": 110
        },
        "total": 110
    },
    {
        "customerName": "PraveenK",
        "monthlyPoints": {
            "May": 90
        },
        "total": 90
    }
]
```
<br>

<b>Business logic and validation implemented in Service layer</b>
<br> Method 1:
<br><b>public String addData()</b>: In this method we are adding sample data into h2 database using Jpa repository and returning String message on successfull.
<br> Method 2:
<br><b>public Customer getCustomerTranscations(String customerName)</b>: In this method we are initially fetching list Transcations did from customer and then further process that records and calculate earned point from customer on monthly basis. This method is used by and #GET api for specific customer info.
<br> Method 3:
<br><b>public List<CustomerReponse> calculateCustomerRewardPoints()</b>: In this method we are initially fetching list Transcations did from all customer and then group them of based on customer id and then we are processing for earned point by customer on monthly basis. This method is used by and #GET api for ALl customer info.

<b>#Entity</b><br>
I have used on entity class : Transcations
```
	private Long id;
	private String customerName;
	private LocalDate transactionDate;
	private double amount;
```
<b>#Model classes</b><br>
I have used reponse model classes as:<br>
a>Customer<br>
b>CustomerReponse

<b>#Repository</b><br>
I have used TranscationRepository which extends JpaRepository that helps us for data base operation performed by JPA<br>





