TOTAL=$(jq '. | length' $1)
declare -A item_type=( ["Alchemist"]="potions" ["Herbalist"]="herbs" ["Elementarist"]="essences")
MERCHANTS=""

for (( i=0; i<$TOTAL; i++ ))
do
    
    NAME=$(jq -r ".[$i].name" $1)
    TYPE=$(jq -r ".[$i].type" $1)

    if [[ "$TYPE" == 'Mage' ]]; then
        continue
    fi

    ITEM_TYPE="${item_type[$TYPE]}"
    MERCHANTS="${MERCHANTS}${NAME}:pl.gda.pg.eti.kask.sa.alchemists.agents.${TYPE}("
    TOTAL_ITEMS=$(jq ".[$i].$ITEM_TYPE | length" $1)

    for (( ii=0; ii<$TOTAL_ITEMS; ii++ ))
    do
        ITEM_NAME=$(jq -r ".[$i].$ITEM_TYPE[$ii]" $1)
        ITEM_NUMBER=$(jq -r ".[$i].prices[$ii]" $1)
        ITEM_PRICE=$(jq -r ".[$i].number[$ii]" $1)

        if [ $ii -ne 0 ]; then
            MERCHANTS="${MERCHANTS},"    
        fi

        MERCHANTS="${MERCHANTS}$ITEM_NAME,$ITEM_PRICE,$ITEM_NUMBER"

    done
    MERCHANTS="${MERCHANTS});"
done

# --- MAGE

MAGE=$(jq 'first(.[] | select(.type=="Mage"))' $1)

NAME=$(echo $MAGE | jq -r '.name')
POTIONS=$(echo $MAGE | jq -r '.potions | join("|")')
HERBS=$(echo $MAGE | jq -r '.herbs | join("|")')
ESSENCES=$(echo $MAGE | jq -r '.essences | join("|")')
MONEY=$(echo $MAGE | jq -r '.money')

MAGE_ARG="${NAME}:pl.gda.pg.eti.kask.sa.alchemists.agents.Mage($MONEY,$POTIONS,$HERBS,$ESSENCES);"
# --- END MAGE

echo "MERCHANT_ARGS=$MERCHANTS" > .env
echo "MAGE_ARG=$MAGE_ARG" >> .env