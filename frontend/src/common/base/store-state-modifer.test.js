import {modifyWithDef} from "./store-state-modifier";

it('Test store state modifier', () => {
    const origin = {a: 2, YY: {Y1: 1, Y2: 2}}
    const def = {b: 'A', XX: {MM: 1, DD: 2}, YY: {Y3: 3}}
    const result = modifyWithDef(origin ,def)

    origin.YY.Y4 = 5

    console.log(JSON.stringify(result, null, 2))
})