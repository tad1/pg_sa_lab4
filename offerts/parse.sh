TOTAL=$(jq '. | length' $1)
declare -A item_type=( ["Alchemist"]="potions" ["Herbalist"]="herbs" ["Elementarist"]="essences")
RES=""

for (( i=0; i<$TOTAL; i++ ))
do
    
    NAME=$(jq -r ".[$i].name" $1)
    TYPE=$(jq -r ".[$i].type" $1)

    ITEM_TYPE="${item_type[$TYPE]}"
    RES="${RES}${NAME}:pl.gda.pg.eti.kask.sa.alchemists.agents.${TYPE}("
    TOTAL_ITEMS=$(jq ".[$i].$ITEM_TYPE | length" $1)

    for (( ii=0; ii<$TOTAL_ITEMS; ii++ ))
    do
        ITEM_NAME=$(jq -r ".[$i].$ITEM_TYPE[$ii]" $1)
        ITEM_NUMBER=$(jq -r ".[$i].prices[$ii]" $1)
        ITEM_PRICE=$(jq -r ".[$i].number[$ii]" $1)
        
        if [ $ii -ne 0 ]; then
            RES="${RES},"    
        fi

        RES="${RES}'$ITEM_NAME',$ITEM_PRICE,$ITEM_NUMBER"

    done
    RES="${RES});"
done

echo "AGENT_ARGS=$RES" > .env