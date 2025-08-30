#!/bin/sh

echo "*********************************************************"
echo "Running git pre-commit hook. Running Ktlint and detect..."
echo "*********************************************************"

OUTPUT="/tmp/commit-analysis-result"

#  KtlintCheck ve detekt çalıştır
./gradlew  ktlintCheck detekt --daemon > ${OUTPUT}
EXIT_CODE=$?

if [ ${EXIT_CODE} -ne 0 ]; then
    cat ${OUTPUT}
    rm ${OUTPUT}
    echo "*********************************************"
    echo "       Pre-commit checks failed              "
    echo "  Please fix the above issues before commit  "
    echo "*********************************************"
    exit ${EXIT_CODE}
else
    rm ${OUTPUT}
    echo "*********************************************"
    echo "   Ktlint and detekt checks passed        "
    echo "*********************************************"
fi
