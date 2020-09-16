HANDLER=io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest
RUNTIME=java11
# TODO: Fill in your function name and role
LAMBDA_ROLE_ARN=
FUNCTION_NAME=QuarkusChatHttpProxy
# TODO: Complete with absolute path of function.zip, format fileb:///path/to/zip
ZIP_FILE=fileb://

#aws lambda delete-function --function-name ${FUNCTION_NAME}

#aws lambda create-function --function-name ${FUNCTION_NAME} \
#  --zip-file ${ZIP_FILE} --handler ${HANDLER} \
#  --runtime ${RUNTIME} \
#  --role ${LAMBDA_ROLE_ARN} \
#  --timeout 15 --memory-size 128

aws lambda update-function-code \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE}