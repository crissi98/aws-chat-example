//Copy this to the shell in localhost:8000/shell to create tables
var params = {
    TableName: 'ChatMessages',
    KeySchema: [{ AttributeName: 'chatId', KeyType: 'HASH' }, { AttributeName: 'timestamp', KeyType: 'RANGE' }],
    AttributeDefinitions: [{  AttributeName: 'chatId', AttributeType: 'N', },
        {  AttributeName: 'timestamp', AttributeType: 'N', }],
    ProvisionedThroughput: { ReadCapacityUnits: 1, WriteCapacityUnits: 1, }
};

dynamodb.createTable(params, function(err, data) {
    if (err) ppJson(err);
    else ppJson(data);
});

params = {
    TableName: 'Chats',
    KeySchema: [{ AttributeName: 'member1', KeyType: 'HASH' }, { AttributeName: 'member2', KeyType: 'RANGE' }],
    AttributeDefinitions: [{  AttributeName: 'member1', AttributeType: 'S', },
        {  AttributeName: 'member2', AttributeType: 'S', }],
    ProvisionedThroughput: { ReadCapacityUnits: 1, WriteCapacityUnits: 1, },
    GlobalSecondaryIndexes:
        [
            {
                "IndexName": "ChatsReversedMembers",
                "KeySchema": [
                    {
                        "AttributeName": "member2",
                        "KeyType": "HASH"
                    },
                    {
                        "AttributeName": "member1",
                        "KeyType": "RANGE"
                    },
                ],
                "Projection": {
                    "ProjectionType": "ALL"
                },
                "ProvisionedThroughput": {
                    "ReadCapacityUnits": 1,
                    "WriteCapacityUnits": 1
                }
            }
        ]
};

dynamodb.createTable(params, function(err, data) {
    if (err) ppJson(err);
    else ppJson(data);
});