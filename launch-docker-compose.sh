# =========================================================================
# This file makes it easy to launch a docker-compose
# =========================================================================

# =========================================================================
# Parameters declaration
# =========================================================================
DIRECTORY="."
DOCKER_COMPOSE_NAME="docker-compose.yml"
REBUILD_IMAGE=""
OPTS=""

usage () {
  echo "Usage: $0 [OPTIONS]
  Options:
    -d <folder>   The folder in which the docker-compose file is located (default: .)
    -n <name>     The name of the docker-compose file (default: docker-compose.yml)
    -r <image>    The name of the image to be rebuilt if necessary (default: none)
    -o <options>  Other options for docker-compose
  "
 exit 1
}

# =========================================================================
# Parameters reading
# =========================================================================
while getopts "d:n:r:o:h" opt; do
  case $opt in
    d) DIRECTORY="$OPTARG" ;;
    n) DOCKER_COMPOSE_NAME="$OPTARG" ;;
    r) REBUILD_IMAGE="$OPTARG" ;;
    o) OPTS="$OPTARG" ;;
    h) usage ;;
    ?) usage ;;
  esac
done

# =========================================================================
# Parameters validation
# =========================================================================
if [ -n "$REBUILD_IMAGE" ]; then
    REBUILD_IMAGE_CMD="--force-recreate --build ${REBUILD_IMAGE}"
fi

# =========================================================================
# Parameters display
# =========================================================================
echo "========================================================================="
echo "  Parameters display"
echo "========================================================================="
echo "  Directory = $DIRECTORY"
echo "  File name = $DOCKER_COMPOSE_NAME"

if [ -n "$REBUILD_IMAGE_CMD" ]; then
  echo "  Rebuild image : $REBUILD_IMAGE"
fi

if [ -n "$OPTS" ]; then
  echo "  Options : $OPTS"
fi

echo "========================================================================="

# =========================================================================
# Command
# =========================================================================
CMD="docker-compose -f ${DIRECTORY}/${DOCKER_COMPOSE_NAME} up -d ${REBUILD_IMAGE_CMD} ${OPTS}"
echo "  Command = $CMD"
$CMD

