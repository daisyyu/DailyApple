src=$1
dst=$2.xml
echo '<?xml version="1.0" encoding="utf-8"?>' > $dst
echo '<resources>' >> $dst
echo '  <string-array name="'$2'">' >> $dst
while read line
do
    echo '      <item>' >> $dst
    echo '          '$line >> $dst
    echo '      </item>' >> $dst
done < $src
echo '  </string-array>' >> $dst
echo '</resources>' >> $dst
