# transaction.service
For run server you have to install vagrant, virtualbox and ansible
vagrant up
Server work on http://localhost:8000/transaction.service/api/
For example, add transaction:
PUT http://localhost:8000/transaction.service/api/transactionservice/transaction/10
Payload: { "amount": 10000, "type": "shopping", "parent_id": 10 }
Response: { "status": "ok" }

