curl -X POST --data-urlencode "payload={\"channel\": \"#testing-bash-script\", \"username\": \"webhookbot\", \"text\": \"This is posted to #testing-bash-script and comes from a bot named webhookbot.\", \"icon_emoji\": \":ghost:\"}" ${{secrets.SLACK_WEBHOOK_URL}}
