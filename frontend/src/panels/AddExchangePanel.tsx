import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import type { ReactElement } from 'react'
import { InputLabel, MenuItem, Radio, RadioGroup, Select } from '@mui/material'
import { sendGetUniverisitesFromCountryRequest } from '../utils/country'
import { FormControl, Button } from '@mui/material'
import Alert from '@mui/material/Alert'
import { FormControlLabel } from '@mui/material'
import { sendGetUniversityMajorsRequest } from '../utils/university-major'
import type { GetUniversityMajorResponse } from '../dtos/university-major/GetUniversityMajorResponse'
import type { UniversityNameLanguage } from '../types/UniversityNameLanguage'
import type { AlertMessage } from '../types/AlertMessage'
import type { University } from '../types/University'
import type { Country } from '../types/Country'
import PanelHeader from '../components/PanelHeader'
import { DatePicker } from "@mui/x-date-pickers/DatePicker"
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import type { Dayjs } from 'dayjs'
import type { CreateExchangeRequest } from '../dtos/exchange/CreateExchangeRequest'
import { sendCreateExchangeRequest } from '../utils/exchange'
import { getGlobalErrorCodes } from '../utils/error'

type Major = GetUniversityMajorResponse

type AddExchangeStage = 
  'Choose university' |
  'Choose major' |
  'Choose time range'

type ChooseUniversitySubpanelProps = {
  countries: Country[];
  getCountries: () => void;
  selectedUniversityId: number | undefined;
  setSelectedUniversityId: (universityId: number) => void;
  setCurrentStage: (stage: AddExchangeStage) => void;
}

type ChooseMajorSubpanelProps = {
  selectedMajorId: number | undefined;
  setSelectedMajorId: (majorId: number) => void;
  setCurrentStage: (stage: AddExchangeStage) => void;
}

type ChooseTimeRangeSubpanel = {
  selectedUniversityId: number | undefined;
  selectedMajorId: number | undefined;
}

type AddExchangePanelProps = {
  countries: Country[];
  getCountries: () => void;
}

const ChooseUniversitySubpanel = (props: ChooseUniversitySubpanelProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(null)
  const [selectedUniversityNameLanguage, setSelectedUniversityNameLanguage] =
    useState<UniversityNameLanguage>('english')
  const [universities, setUniversities] = useState<University[]>([])
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const getUniversitiesFromCountry = async () => {
    if (selectedCountryId === null) {
      return
    }
    setUniversities([])
    const result = await sendGetUniverisitesFromCountryRequest(selectedCountryId)
    if (result.isSuccess) {
      setUniversities(result.data)
    } else {
      setMessage({type: 'error', content: 'Failed to load universities.'})
    }
  }

  const getInteractionElement = (universityId: number): ReactElement => {
    if ((props.selectedUniversityId === universityId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={() => {
            props.setSelectedUniversityId(universityId)
            props.setCurrentStage('Choose major')
          }}>
          NEXT STEP
        </Button>
      )
    }
    return <></>
  }

  useEffect(() => {
    props.getCountries()
  }, [])

  useEffect(() => {
    if (props.countries.length > 0) {
      setMessage(null)
      return
    }

    const timeout = setTimeout(() => {
      if (props.countries.length === 0) {
        setMessage({ type: 'error', content: 'Failed to load countries.' })
      }
    }, 5000)

    return () => clearTimeout(timeout)
  }, [props.countries])

  useEffect(() => {
    getUniversitiesFromCountry()
  }, [selectedCountryId])

  useEffect(() => {
    setMessage(null)
  }, [props.selectedUniversityId, selectedCountryId])

  return (
    <Box sx={{display: 'flex', flexDirection: 'column'}}>
      <PanelHeader label='Find university where you were on exchange'/>
      <Box sx={{display: 'flex', flexDirection: {xs: 'column', md:'row'}}}>
        <FormControl sx={{ m: 2, width: {xs: '100%', sm: '60%', lg: '40%'}}}>
          <InputLabel>Country</InputLabel>
          <Select id='select-country-id' autoWidth label='Country'
            value={selectedCountryId}
            onChange={e => setSelectedCountryId(Number(e.target.value))}>
            {props.countries.map(c => (
              c.id !== 0 ? (
                <MenuItem key={c.id} value={c.id}>
                  {c.name}
                  <img src={`/flags/${c.name}.png`} style={{height: '0.8rem', marginLeft: 3}} />
                </MenuItem>
              ) : null
            ))}
          </Select>
        </FormControl>
        <FormControl sx={{m: 2, width: {xs: '50%', sm: '30%', lg: '20%'}}}>
          <InputLabel>University name language</InputLabel>
          <Select id='select-university-name-language-id' autoWidth 
            label='University name language'
            value={selectedUniversityNameLanguage}
            onChange={() => setSelectedUniversityNameLanguage(
              prevState => (prevState === 'english' ? 'native' : 'english'))}>
              <MenuItem key={'english'} value={'english'}>english</MenuItem>
              <MenuItem key={'native'} value={'native'}>native</MenuItem>
          </Select>
        </FormControl>
      </Box>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <FormControl>
        <RadioGroup name='universities-buttons-group'
          value={props.selectedUniversityId}
          onChange={(event) => {
            props.setSelectedUniversityId(Number(event.target.value))
          }}>
          {universities.map(u => (
            <FormControlLabel key={u.id} value={u.id} control={<Radio/>} label={(
              <>
                {(selectedUniversityNameLanguage === 'english' && u.englishName) ?
                  u.englishName :
                  u.nativeName}
                {` [${u.city.name}]`}
                {getInteractionElement(u.id)}
              </>)}
          />))}
        </RadioGroup>
      </FormControl>
    </Box>
  )
}

const ChooseMajorSubpanel = (props: ChooseMajorSubpanelProps) => {
  const [majors, setMajors] = useState<Major[]>([])
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const getInteractionElement = (majorId: number): ReactElement => {
    if ((props.selectedMajorId === majorId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={() => {
            props.setSelectedMajorId(majorId)
            props.setCurrentStage('Choose time range')
          }}>
          NEXT STEP
        </Button>
      )
    }
    return <></>
  }

  const getMajors = async () => {
    const result = await sendGetUniversityMajorsRequest()
    if (result.isSuccess) {
      const majors = result.data
      setMajors(majors)
    } else {
      setMessage({type: 'error', content: 'Failed to load universities.'})
    }
  }

  useEffect(() => {
    getMajors()
  }, []) 

  return (
    <Box sx={{display: 'flex', flexDirection: 'column'}}>
      <PanelHeader label='Find your university major'/>
      <Box sx={{display: 'flex', flexDirection: {xs: 'column', md:'row'}}}>
        <RadioGroup name='universities-buttons-group'
          value={props.selectedMajorId}
          onChange={(event) => {
            props.setSelectedMajorId(Number(event.target.value))
          }}>
          {majors.map(m => (
            <FormControlLabel key={m.id} value={m.id} control={<Radio/>} label={(
              <>
                {m.name}
                {getInteractionElement(m.id)}
              </>)}
          />))}
        </RadioGroup>
      </Box>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
    </Box>
  )
}

const ChooseTimeRangeSubpanel = (props: ChooseTimeRangeSubpanel) => {
  const [startDate, setStartDate] = useState<Dayjs | null>(null)
  const [endDate, setEndDate] = useState<Dayjs | null>(null)
  const [message, setMesage] = useState<AlertMessage | null>(null)
  const [shouldButtonBeShown, setShouldButtonBeShown] = useState<boolean>(false)
  const [wasExchangeAddedSuccessfully, setWasExchangeAddedSuccessfully] = useState<boolean>(false)

  const handleAddingExchange = async () => {
    if (!props.selectedUniversityId || !props.selectedMajorId || 
      !startDate || !endDate) {
      setMesage({type: 'error', content: 'An error occured please try again later.'})
      return
    }
    const formattedStartDate = startDate.format('YYYY-MM-DD')
    const formattedEndDate = endDate.format('YYYY-MM-DD')
    const request: CreateExchangeRequest = {
      startedAt: formattedStartDate,
      endAt: formattedEndDate,
      universityId: props.selectedUniversityId,
      universityMajorId: props.selectedMajorId
    }
    setMesage({type: 'info', content: 'Waiting for server response.'})
    setShouldButtonBeShown(false)
    const response = await sendCreateExchangeRequest(request)
    if (response.isSuccess) {
      setMesage({type: 'success', content: 'Exchange was successfully added.'})
      setWasExchangeAddedSuccessfully(true)
    } else {
      const globalErrorsCodes = getGlobalErrorCodes(response.error)
      if (globalErrorsCodes.length !== 0) {
        if (globalErrorsCodes.includes('ValidDateRange')) {
          setMesage({type: 'error', content: 'Start date must be before end date.'})
        } else {
          setMesage({type: 'error', content: 'An error occured please try again later.'})
        }
      }
    }
  }

  useEffect(() => {
    setShouldButtonBeShown(!!(startDate && endDate))
  }, [startDate, endDate])

  return (
    <Box sx={{display: 'flex', flexDirection: 'column'}}>
      <PanelHeader label='Pick boundary dates for your exchange'/>
      {message && 
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert>
      }
      {!wasExchangeAddedSuccessfully &&
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <Box display="flex" gap={2}>
            <DatePicker
              label="Start date"
              value={startDate}
              onChange={(newValue) => setStartDate(newValue)}/>
            <DatePicker
              label="End date"
              value={endDate}
              onChange={(newValue) => setEndDate(newValue)}/>
          </Box>
          {shouldButtonBeShown &&
            <Button variant='contained' size='medium' sx={{marginLeft: 2, 
              marginTop: 2, width: {xs: '50%', md: '25%'}}}
              onClick={() => {
                handleAddingExchange()
              }}>
              Add exchange
            </Button>
          }
        </LocalizationProvider>
      }
    </Box>
  )
}

const AddExchangePanel = (props: AddExchangePanelProps) => {
  const [currentStage, setCurrentStage] = useState<AddExchangeStage>('Choose university')
  const [selectedUniversityId, setSelectedUniversityId] = useState<number | undefined>(undefined)
  const [selectedMajorId, setSelectedMajorId] = useState<number | undefined>(undefined)

  const getStagePanel = () => {
    if (currentStage === 'Choose university') {
      return <ChooseUniversitySubpanel countries={props.countries} 
        getCountries={props.getCountries} 
        selectedUniversityId={selectedUniversityId}
        setSelectedUniversityId={setSelectedUniversityId}
        setCurrentStage={setCurrentStage}/>
    } else if (currentStage === 'Choose major') {
      return <ChooseMajorSubpanel selectedMajorId={selectedMajorId}
        setSelectedMajorId={setSelectedMajorId} setCurrentStage={setCurrentStage}/>
    } else if (currentStage === 'Choose time range') {
      return <ChooseTimeRangeSubpanel selectedMajorId={selectedMajorId}
        selectedUniversityId={selectedUniversityId}/>
    }
  }

  return (
    <Box>
      {getStagePanel()}
    </Box>
  )
}

export default AddExchangePanel