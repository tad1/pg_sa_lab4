JQ="./jq/jq-linux-i386"
TOTAL=$($JQ '. | length' $1)
declare -A item_type=( ["Alchemist"]="potions" ["Herbalist"]="herbs" ["Elementarist"]="essences")
MERCHANTS=""

for (( i=0; i<$TOTAL; i++ ))
do
    
    NAME=$($JQ -r ".[$i].name" $1)
    TYPE=$($JQ -r ".[$i].type" $1)

    if [[ "$TYPE" == 'Mage' ]]; then
        continue
    fi

    ITEM_TYPE="${item_type[$TYPE]}"
    MERCHANTS="${MERCHANTS}${NAME}:pl.gda.pg.eti.kask.sa.alchemists.agents.${TYPE}("
    TOTAL_ITEMS=$($JQ ".[$i].$ITEM_TYPE | length" $1)

    for (( ii=0; ii<$TOTAL_ITEMS; ii++ ))
    do
        ITEM_NAME=$($JQ -r ".[$i].$ITEM_TYPE[$ii]" $1)
        ITEM_NUMBER=$($JQ -r ".[$i].number[$ii]" $1)
        ITEM_PRICE=$($JQ -r ".[$i].prices[$ii]" $1)

        if [ $ii -ne 0 ]; then
            MERCHANTS="${MERCHANTS},"    
        fi

        MERCHANTS="${MERCHANTS}$ITEM_NAME,$ITEM_PRICE,$ITEM_NUMBER"

    done
    DELAY=$($JQ ".[$i] | if has(\"delay\") then .delay else 0 end" $1)
    MERCHANTS="${MERCHANTS},${DELAY});"
done

# --- MAGE
MAGES=$($JQ '[.[] | select(.type=="Mage")]' $1)
N_MAGES=$(echo $MAGES | $JQ '. | length')
MAGE_ARG=""

for (( i=0; i<$N_MAGES; i++ ))
do
MAGE=$(echo $MAGES | $JQ ".[$i]")
NAME=$(echo $MAGE | $JQ -r '.name')
POTIONS=$(echo $MAGE | $JQ -r '.potions | join("|")')
HERBS=$(echo $MAGE | $JQ -r '.herbs | join("|")')
ESSENCES=$(echo $MAGE | $JQ -r '.essences | join("|")')
MONEY=$(echo $MAGE | $JQ -r '.money')
DELAY=$(echo $MAGE | $JQ ". | if has(\"delay\") then .delay else 0 end")


MAGE_ARG="${MAGE_ARG}${NAME}:pl.gda.pg.eti.kask.sa.alchemists.agents.Mage($MONEY,$POTIONS,$HERBS,$ESSENCES, $DELAY);"

done
# --- END MAGE

echo "MERCHANT_ARGS=$MERCHANTS" > .env
echo "MAGE_ARG=$MAGE_ARG" >> .env