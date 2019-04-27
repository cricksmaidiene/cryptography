urlhist=()
index=-1
 
clear  
echo "Enter a URL (for displaying content  in text mode)"
echo "Enter b for back "
echo "Enter q to quit. "
 
while true
do
    echo -e '\n'
    echo  "Enter your selection"
    read url
 
    case $url in
        b )
            if (($index<1));
            then
                echo "Nothing to go back to"
                if (($index == 0)); then
                    ((index--))
                fi
            else
                ((index--))
                links -dump ${urlhist[$index]}
            fi
            echo -e '\n\n\n'
            echo "curent index is "$index
        ;;
        q )
            echo "                         Goodbye"
            exit
        ;;
        https://*|http://*|www.*)
            echo "URL processing Please wait"
            ((index++))
            urlhist[$index]=$url
            links -dump $url
            echo -e '\n\n\n'
            echo "curent index is "$index
        ;;
        *)
            echo "INVALID"
            echo Enter URL starting with http:// or https://
             ;;
    esac
    echo "----------------------------------------------------------------"
done
