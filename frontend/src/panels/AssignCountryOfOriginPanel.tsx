import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import type { ReactElement } from 'react'
import { Radio, RadioGroup } from '@mui/material'
import { FormControl, Button } from '@mui/material'
import { sendAssignCountryOfOriginRequest } from '../utils/user'
import Alert from '@mui/material/Alert'
import { FormControlLabel } from '@mui/material'
import type { AlertMessage } from '../types/AlertMessage'
import type { Country } from '../types/Country'
import PanelHeader from '../components/PanelHeader'

type AssignCountryOfOriginPanelProps = {
  countries: Country[];
  getCountries: () => void;
}

const AssignCountryOfOriginPanel = (props: AssignCountryOfOriginPanelProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(null)
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const handleCountryAssignment = async () => {
    const countryId = selectedCountryId !== 0 ? selectedCountryId : null
    setMessage({type: 'info', content: 'Waiting for server response.'})
    const result = await sendAssignCountryOfOriginRequest({countryId: countryId})
    if (result.isSuccess) {
      setMessage({type: 'success', content: 'Country was assigned to user successfully.'})
    } else {
      setMessage({type: 'error', content: 'Failed to assign country. Please try again later.'})
    }
  }

  const getInteractionElement = (countryId: number): ReactElement => {
    if ((selectedCountryId === countryId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={handleCountryAssignment}>
          CONFIRM
        </Button>
      )
    }
    return <></>
  }

  useEffect(() => {
    props.getCountries()
  }, [])

  useEffect(() => {
    setMessage(null)
  }, [selectedCountryId])

  return (
    <Box>
      <PanelHeader label='Assign country of origin to yourself'/>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <FormControl>
        <RadioGroup name='countries-buttons-group'
          value={selectedCountryId}
          onChange={(event) => {
            setSelectedCountryId(Number(event.target.value))
          }}>
          {props.countries.map(c => (
            <FormControlLabel key={c.id} value={c.id} control={<Radio/>} label={(
              <>
                {c.name}
                {c.id !== 0 ?
                  <img src={`/flags/${c.name}.png`} 
                    style={{height: '0.8rem', marginLeft: 3}}/> : 
                  <></>
                }
                {getInteractionElement(c.id)}
              </>)}
          />))}
        </RadioGroup>
      </FormControl>
    </Box>
  )
}

export default AssignCountryOfOriginPanel