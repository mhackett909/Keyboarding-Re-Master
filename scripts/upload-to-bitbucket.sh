#!/bin/bash

if [ $# -lt 4 ] || [ -z $1  -o  -z $2  -o  -z $3  -o  -z $4 ]; then
    cat <<-EOF
    [!] error: missing arguments :(

     syntax: upload-to-bitbucket.sh <user> <password> <repo downloads page> <local file to upload>
    example: upload-to-bitbucket.sh swyter secret1 /Swyter/tld-downloads/downloads myfile.zip

    tip: if a file with the same name is already in there it will be overwrited without notice

EOF

    exit
fi
    usr=$1; pwd=$2; pge=$3; fil=$4

    # now seems to work like this: https://confluence.atlassian.com/bitbucket/downloads-resource-812221853.html#downloadsResource-POSTafile

    echo "actual upload progress should appear right now as a progress bar, be patient:"
    curl --insecure               `# make tls cert validation optional, read this if you need it (https://curl.haxx.se/docs/sslcerts.html) ` \
         --progress-bar           `# print the progress visually                                                                          ` \
         --output ./utbb          `# avoid outputting anything apart from that neat bar                                                  ` \
         --location               `# follow redirects if we are told so                                                                 ` \
         --fail                   `# ensure that we are not succeeding when the server replies okay but with an error code             ` \
         --write-out %{http_code} `# write the returned error code to stdout, we will redirect it later to a file for parsing         ` \
         --user "$usr:$pwd"       `# basic auth so that it lets us in                                                                ` \
         --form files=@"$fil" "https://api.bitbucket.org/2.0/repositories/${pge#/}" 1> utbb_httpcode # <- that special #/ thing trims initial slashes, if any

    # -> when curl proceeds okay but the server is not happy:
if [ -f ./utbb ] && [ ! -z "$(grep error ./utbb)" ]; then
    cat <<-EOF

    [!] server error: bitbucket (the platform) returned a message for us to see:

    $(cat ./utbb)

EOF

    # custom error code for server-side issues
    exit 255
fi

    # -> when the server responds with an http code other than 201 (created):
if [ -f ./utbb_httpcode ] && [ ! -z "$(cat ./utbb_httpcode)" ] && [ "$(cat ./utbb_httpcode)" -ne 201 ]; then
    cat <<-EOF

    [!] server error: bitbucket (the platform) returned HTTP error code $(cat ./utbb_httpcode)

EOF

    # custom error code for server-side issues
    exit 254
fi

    # -> when curl has general connectivity problems at network level:
if [ $? -ne 0 ]; then
    cat <<-EOF

    [!] error: curl returned exit code $?... upload canceled!
               to see what the number means go here:
               <https://curl.haxx.se/docs/manpage.html#EXIT>

EOF
fi
    # return the same error for further script processing
    exit $?
