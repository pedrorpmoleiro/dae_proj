<template>
    <!--v-container>
        <v-data-table :fields="atletasFields" :items="atletas">
            <template v-slot:top>
                <h3>Atletas</h3>
            </template>
        </v-data-table>
    </v-container-->
    <div>
        <h3>Entrooo</h3>
    </div>
    
</template>

<script>
    export default {
        data() {
            return {
                escalao: {},
                atletasFields: [ 'username', 'name' ]
            }
        },
        computed: {
            nome() {
                return this.$route.params.nome
            },
            atletas() {
                return this.escalao.atletas || []
            }
        },
        created() {
        }
    }
</script>

<style scoped>

</style>